package app.prgghale.roomdb.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.prgghale.roomdb.data.dao.UsersDao
import app.prgghale.roomdb.data.domain.UserProfession
import app.prgghale.roomdb.data.repository.UserRepository
import app.prgghale.roomdb.domain.SuspendingWorkInteractor
import app.prgghale.roomdb.utils.ObservableLoadingCounter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    override suspend fun doWork(params: Params): List<UserProfession> {
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

