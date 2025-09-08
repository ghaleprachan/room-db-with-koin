package app.prgghale.roomdb.ui.loading.observers

import app.prgghale.roomdb.data.repository.UserRepository
import app.prgghale.roomdb.data.table.UserTable
import app.prgghale.roomdb.domain.SubjectInteractor
import app.prgghale.roomdb.utils.UiStates
import kotlinx.coroutines.flow.flow

class UserObserver(
    private val userRepository: UserRepository,
) : SubjectInteractor<UserObserver.Params, UiStates<List<UserTable>>>() {

    override fun createObservable(params: Params) = flow {
        emit(userRepository.getUsers())
    }

    data class Params(val action: String = app.prgghale.roomdb.Action.Fetch)
}