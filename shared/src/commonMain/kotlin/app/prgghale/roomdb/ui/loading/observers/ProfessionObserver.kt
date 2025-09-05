package app.prgghale.roomdb.ui.loading.observers

import app.prgghale.roomdb.data.repository.UserRepository
import app.prgghale.roomdb.data.table.ProfessionTable
import app.prgghale.roomdb.domain.SubjectInteractor
import app.prgghale.roomdb.utils.UiStates
import kotlinx.coroutines.flow.flow


class ProfessionObserver(private val repository: UserRepository) :
    SubjectInteractor<ProfessionObserver.Params, UiStates<List<ProfessionTable>>>() {

    data class Params(val action: String = app.prgghale.roomdb.Action.Fetch)

    override fun createObservable(params: Params) = flow {
        emit(repository.getProfessionsState())
    }
}
