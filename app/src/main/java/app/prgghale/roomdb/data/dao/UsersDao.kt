package app.prgghale.roomdb.data.dao

import androidx.room.Dao
import androidx.room.Query
import app.prgghale.roomdb.data.table.UserTable

@Dao
interface UsersDao : BaseDao<UserTable> {

    @Query("SELECT * FROM user_table")
    fun getUsers(): List<UserTable>

    @Query("DELETE FROM user_table")
    fun deleteAll()

}