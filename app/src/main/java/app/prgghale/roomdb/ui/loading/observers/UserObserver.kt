package app.prgghale.roomdb.ui.loading.observers

import app.prgghale.roomdb.data.repository.UserRepository
import app.prgghale.roomdb.data.table.UserTable
import app.prgghale.roomdb.ui.search.SuspendingWorkInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class UserObserver(
    private val userRepository: UserRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : SuspendingWorkInteractor<UserObserver.Params, List<UserTable>>() {
    override suspend fun doWork(params: Params): List<UserTable> {
        return withContext(coroutineDispatcher) {
            userRepository.getUsersT()
        }
    }

    data class Params(val count: Int = 20)
}