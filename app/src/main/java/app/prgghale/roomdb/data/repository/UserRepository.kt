package app.prgghale.roomdb.data.repository

import app.prgghale.roomdb.data.dao.UsersDao
import app.prgghale.roomdb.data.table.UserTable
import app.prgghale.roomdb.utils.UiStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

interface UserRepository {
    suspend fun addUser(user: UserTable): UiStates<Boolean>
    suspend fun getUsers(): UiStates<List<UserTable>>
}

class UserRepoImpl(
    private val usersDao: UsersDao
) : UserRepository {
    override suspend fun addUser(user: UserTable): UiStates<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                usersDao.insert(user)
                UiStates.Success(true)
            } catch (ex: Exception) {
                UiStates.Error(message = ex.message)
            }
        }
    }

    override suspend fun getUsers(): UiStates<List<UserTable>> {
        return withContext(Dispatchers.IO) {
            try {
                UiStates.Success(data = usersDao.getUsers())
            } catch (ex: Exception) {
                UiStates.Error(message = ex.message)
            }
        }
    }
}