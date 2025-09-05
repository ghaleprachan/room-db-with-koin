package app.prgghale.roomdb.ui.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.prgghale.roomdb.ui.loading.observers.ProfessionObserver
import app.prgghale.roomdb.ui.loading.observers.UserObserver
import app.prgghale.roomdb.ui.loading.observers.UserProfessionObserver
import app.prgghale.roomdb.utils.ObservableLoadingCounter
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class LoadingViewModel(
    private val userObserver: UserObserver,
    private val professionObserver: ProfessionObserver,
    private val userProfessionObserver: UserProfessionObserver,
) : ViewModel() {

    private val userLoadingState = ObservableLoadingCounter()
    private val professionLoadingState = ObservableLoadingCounter()
    private val userProfLoadingState = ObservableLoadingCounter()

    init {
        refresh()
    }

    fun refresh() {
        professionObserver.invoke(ProfessionObserver.Params(app.prgghale.roomdb.Action.Fetch))
        userProfessionObserver.invoke(UserProfessionObserver.Params(app.prgghale.roomdb.Action.Fetch))
        userObserver.invoke(UserObserver.Params(app.prgghale.roomdb.Action.Fetch))
    }
    /*val state = userProfessionObserver.flow.zip(professionObserver.flow) { user, profession ->
        LoadingViewState(
            userProfessions = user,
            professions = profession
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(0),
        initialValue = LoadingViewState.Empty
    )*/

    val myState = combine(
        userProfessionObserver.flow,
        professionObserver.flow,
        userObserver.flow
    ) { userProfession, professions, users ->
        LoadingViewState(
            user = users,
            professions = professions,
            userProfessions = userProfession
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(0),
        initialValue = LoadingViewState.Empty
    )
}

