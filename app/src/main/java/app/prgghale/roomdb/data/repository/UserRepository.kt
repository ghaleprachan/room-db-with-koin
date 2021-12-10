package app.prgghale.roomdb.data.repository

import android.util.Log
import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import java.lang.Exception

interface UserRepository {
    suspend fun addUser(user: UserTable): UiStates<Boolean>
    suspend fun updateUser(user: UserTable): UiStates<Boolean>
    suspend fun getUsers(): UiStates<List<UserTable>>
    suspend fun getUserProfession(): UiStates<List<UserProfession>>
    suspend fun deleteUser(user: UserTable): UiStates<Boolean>
    suspend fun getProfessions(): List<ProfessionTable>
    suspend fun getFavoriteUsers(): List<UserTable>

    suspend fun getProfessionsState(): UiStates<List<ProfessionTable>>
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
                delay(1000)
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
                delay(2000)
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

    override suspend fun getProfessionsState(): UiStates<List<ProfessionTable>> {
        return withContext(dispatcher) {
            try {
                delay(3000)
                val professions = professionDao.getAllProfessions()
                UiStates.Success(data = professions)
            } catch (ex: Exception) {
                UiStates.Error(message = ex.message.orEmpty())
            }
        }
    }

    /*val calls = listOf(
                async { usersDao.getUsers() },
                async { professionDao.getAllProfessions() }
            )
            calls.awaitAll()*/
    /*override suspend fun getUserProfessionF(): UiStates<UserProfessionT> {
        return withContext(dispatcher) {
            val user = async { usersDao.getUsers() }
            val userResponse = user.await()
            Log.e("PrachanGhale", "Above User")
            delay(3000)
            Log.e("PrachanGhale", "Below User")
            val professions = async { professionDao.getAllProfessions() }
            UiStates.Success(UserProfessionT(userResponse, professions.await()))
        }
    }*/


    /**
     * [distinctUntilChanged()] is a filtering operator,
     * emitting a subset of elements from the upstream flow.
     * In this case, the filtering rule is "eliminate consecutive
     * duplicates". So, in this case, the 1, 1, 1 portion of our
     * flow will result in a single 1 being emitted downstream,
     * as the duplicates are ignored.
     * */
    /* override suspend fun getUsersT(): List<UserTable> {
         return try {
             Log.e("PrachanGhale", "Here finally")
             usersDao.getUsers()
         } catch (e: Exception) {
             Log.e("PrachanGhale", "${e.message}")
             emptyList()
         }
     }

     override suspend fun getUsersT2() = flow<List<UserTable>> {
         usersDao.getUsers()
     }.catch { emptyList<UserTable>() }.distinctUntilChanged()

     override suspend fun getProfessionT() = flow<List<ProfessionTable>> {
         professionDao.getAllProfessions()
     }.catch { emptyList<ProfessionTable>() }
         .distinctUntilChanged()


     override suspend fun getUserProfessionT() = flow<List<UserProfession>> {
         try {
             usersDao.getUserProfession()
         } catch (ex: Exception) {
             emptyList()
         }
     }.distinctUntilChanged()*/
}