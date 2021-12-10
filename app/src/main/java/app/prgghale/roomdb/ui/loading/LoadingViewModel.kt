package app.prgghale.roomdb.ui.loading

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.prgghale.roomdb.data.domain.UserProfession
import app.prgghale.roomdb.data.repository.UserRepository
import app.prgghale.roomdb.data.table.ProfessionTable
import app.prgghale.roomdb.data.table.UserTable
import app.prgghale.roomdb.extesion.toJson
import app.prgghale.roomdb.ui.loading.observers.UserObserver
import app.prgghale.roomdb.utils.UiStates
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoadingViewModel(
    private val userRepository: UserRepository,
    userObserver: UserObserver
) : ViewModel() {
    private val userLoadingState = ObservableLoadingCounter()
    private val professionLoadingState = ObservableLoadingCounter()
    private val userProfLoadingState = ObservableLoadingCounter()


    init {
        userObserver.invoke(UserObserver.Params(10))
        onRefresh()
    }

    private val professions = flow<List<ProfessionTable>> {
        userRepository.getProfessionT()
    }
    private val userProfessions = flow<List<UserProfession>> {
        userRepository.getUserProfession()
    }

    val state: StateFlow<LoadingViewState> =
        combine(
            userLoadingState.observable,
            professionLoadingState.observable,
            userProfLoadingState.observable,
            userObserver.flow,
            professions,
            userProfessions,
        ) { userLoad, professionLoad, userProfLoad, users, professions, userProfession ->
            Log.e("PrachanGhale", users.toJson())
            LoadingViewState(
                userRefreshing = userLoad,
                professionsRefreshing = professionLoad,
                userProfRefreshing = userProfLoad,
                user = users,
                professions = professions,
                userProfessions = userProfession
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LoadingViewState.Empty
        )

    private fun onRefresh() {
        viewModelScope.launch {

        }
    }
}

data class UserStateFlow(
    val loading: Boolean = false,
    val userData: UiStates<List<ProfessionTable>> = UiStates.None(),
)

