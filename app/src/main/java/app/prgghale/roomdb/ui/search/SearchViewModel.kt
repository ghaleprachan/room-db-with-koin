package app.prgghale.roomdb.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import app.prgghale.roomdb.data.dao.UsersDao
import app.prgghale.roomdb.data.domain.UserProfession
import app.prgghale.roomdb.data.repository.UserRepository
import app.prgghale.roomdb.data.table.UserTable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger

class SearchViewModel(
    private val searchUser: SearchUser
) : ViewModel() {
    private val searchQuery = MutableStateFlow("")
    private val loadingState = ObservableLoadingCounter()
    private val pendingActions = MutableSharedFlow<SearchAction>()

    val state: StateFlow<SearchViewState> = combine(
        searchUser.flow,
        loadingState.observable
    ) { results, refreshing ->
        SearchViewState(
            searchResult = results,
            refreshing = refreshing
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SearchViewState.Empty
    )

    init {
        viewModelScope.launch {
            searchQuery.debounce(300)
                .collectLatest { query ->
                    val job = launch {
                        loadingState.addLoader()
                        searchUser(SearchUser.Params(query = query))
                    }
                    job.invokeOnCompletion { loadingState.removeLoader() }
                    job.join()
                }
        }

        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    is SearchAction.Search -> {
                        searchQuery.value = action.searchTerm
                    }
                    else -> {}
                }
            }
        }
    }

    fun submitAction(action: SearchAction) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }
}

sealed class SearchAction {
    data class OpenShowDetails(val showId: Long) : SearchAction()
    data class Search(val searchTerm: String = "") : SearchAction()
}

class ObservableLoadingCounter {
    private val count = AtomicInteger()
    private val loadingState = MutableStateFlow(count.get())
    val observable: Flow<Boolean> get() = loadingState.map { it > 0 }.distinctUntilChanged()

    fun addLoader() {
        loadingState.value = count.incrementAndGet()
    }

    fun removeLoader() {
        loadingState.value = count.decrementAndGet()
    }
}

data class SearchViewState(
    val query: String = "",
    val searchResult: List<UserProfession> = emptyList(),
    val refreshing: Boolean = false
) {
    companion object {
        val Empty = SearchViewState()
    }
}

/**
 * If has to get data from network that user also can use network repository
 * Here i have not used [userRepository] i have accessed data direct from [usersDao]*/
class SearchUser(
    private val userRepository: UserRepository,
    private val usersDao: UsersDao,
    private val dispatcher: CoroutineDispatcher
) : SuspendingWorkInteractor<SearchUser.Params, List<UserProfession>>() {
    override suspend fun doWork(params: SearchUser.Params): List<UserProfession> {
        return withContext(dispatcher) {
            val remoteResults = emptyList<UserProfession>()/*userRepository.getUsers()*/
            remoteResults.ifEmpty {
                when {
                    params.query.isNotBlank() -> usersDao.search("%${params.query}%")
                    else -> usersDao.getUserProfession()
                }
            }
        }
    }

    data class Params(val query: String)
}

abstract class SuspendingWorkInteractor<P : Any, T> : SubjectInteractor<P, T>() {
    override fun createObservable(params: P): Flow<T> = flow {
        emit(doWork(params = params))
    }

    abstract suspend fun doWork(params: P): T
}


abstract class SubjectInteractor<P : Any, T> {
    private val paramState = MutableSharedFlow<P>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val flow: Flow<T> = paramState
        .distinctUntilChanged()
        .flatMapLatest { createObservable(it) }
        .distinctUntilChanged()

    operator fun invoke(params: P) {
        paramState.tryEmit(params)
    }

    protected abstract fun createObservable(params: P): Flow<T>
}