package app.prgghale.roomdb.data.repository

import android.util.Log
import app.prgghale.roomdb.data.dao.ProfessionDao
import app.prgghale.roomdb.data.dao.UsersDao
import app.prgghale.roomdb.data.domain.UserProfession
import app.prgghale.roomdb.data.domain.UserProfessionT
import app.prgghale.roomdb.data.table.ProfessionTable
import app.prgghale.roomdb.data.table.UserTable
import app.prgghale.roomdb.utils.UiStates
import app.prgghale.roomdb.utils.handleTryCatch
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

interface UserRepository {
    suspend fun addUser(user: UserTable): UiStates<Boolean>
    suspend fun updateUser(user: UserTable): UiStates<Boolean>
    suspend fun getUsers(): UiStates<List<UserTable>>
    suspend fun getUserProfession(): UiStates<List<UserProfession>>
    suspend fun deleteUser(user: UserTable): UiStates<Boolean>
    suspend fun getProfessions(): List<ProfessionTable>
    suspend fun getFavoriteUsers(): List<UserTable>


    suspend fun getUserProfessionF(): UiStates<UserProfessionT>
}

class UserRepoImpl(
    private val usersDao: UsersDao,
    private val professionDao: ProfessionDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {
    override suspend fun addUser(user: UserTable): UiStates<Boolean> {
        return withContext(dispatcher) {
            handleTryCatch {
                usersDao.insert(user)
                UiStates.Success(true)
            }
        }
    }

    override suspend fun updateUser(user: UserTable): UiStates<Boolean> {
        return withContext(Dispatchers.IO) {
            handleTryCatch {
                usersDao.update(user)
                UiStates.Success(true)
            }
        }
    }

    override suspend fun getUsers(): UiStates<List<UserTable>> {
        return withContext(Dispatchers.IO) {
            handleTryCatch {
                UiStates.Success(data = usersDao.getUsers())
            }
        }
    }

    override suspend fun getUserProfession(): UiStates<List<UserProfession>> {
        return withContext(Dispatchers.IO) {
            handleTryCatch {
                UiStates.Success(data = usersDao.getUserProfession())
            }
        }
    }

    override suspend fun deleteUser(user: UserTable): UiStates<Boolean> {
        return withContext(Dispatchers.IO) {
            handleTryCatch {
                usersDao.delete(user)
                UiStates.Success(true)
            }
        }
    }

    override suspend fun getProfessions(): List<ProfessionTable> {
        return withContext(Dispatchers.IO) {
            try {
                professionDao.getAllProfessions()
            } catch (ex: Exception) {
                Log.e("RoomDbERR", ex.message ?: "Failed to fetch professions")
                emptyList()
            }
        }
    }

    override suspend fun getFavoriteUsers(): List<UserTable> {
        return withContext(Dispatchers.IO) {
            try {
                usersDao.getFavoriteUsers()
            } catch (ex: Exception) {
                Log.e("RoomDbERR", ex.message ?: "Failed get favorite users")
                emptyList()
            }
        }
    }

    /*val calls = listOf(
                async { usersDao.getUsers() },
                async { professionDao.getAllProfessions() }
            )
            calls.awaitAll()*/
    override suspend fun getUserProfessionF(): UiStates<UserProfessionT> {
        return withContext(dispatcher) {
            val user = async { usersDao.getUsers() }
            delay(3000)
            val professions = async { professionDao.getAllProfessions() }
            UiStates.Success(UserProfessionT(user.await(), professions.await()))
        }
    }
}