package app.prgghale.roomdb.ui.loading.observers

import app.prgghale.roomdb.data.domain.UserProfession
import app.prgghale.roomdb.data.repository.UserRepository
import app.prgghale.roomdb.domain.SubjectInteractor
import app.prgghale.roomdb.utils.UiStates
import kotlinx.coroutines.flow.flow

class UserProfessionObserver(
    private val userRepository: UserRepository
) :
    SubjectInteractor<UserProfessionObserver.Params, UiStates<List<UserProfession>>>() {

    data class Params(val action: String = app.prgghale.roomdb.Action.Fetch)

    override fun createObservable(params: Params) = flow {
        emit(userRepository.getUserProfession())
    }
}