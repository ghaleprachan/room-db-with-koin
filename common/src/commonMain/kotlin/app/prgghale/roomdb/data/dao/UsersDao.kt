package app.prgghale.roomdb.data.dao

import androidx.room.Dao
import androidx.room.Query
import app.prgghale.roomdb.data.domain.UserProfession
import app.prgghale.roomdb.data.table.UserTable

/**
 * NOTE
 * An inner join finds and returns matching data from tables, while an outer join finds and returns matching data and some dissimilar data from tables.*/
@Dao
interface UsersDao : BaseDao<UserTable> {

    @Query("SELECT * FROM user_table")
    fun getUsers(): List<UserTable>

    @Query("DELETE FROM user_table")
    fun deleteAll()

    @Query(
        """
            SELECT * FROM user_table 
                INNER JOIN profession_table
                ON user_table.profession = profession_table.profession_id
            """
    )
    fun getUserProfession(): List<UserProfession>

    @Query(
        """
        SELECT * FROM user_table WHERE isFavorite=:isFavorite
    """
    )
    fun getFavoriteUsers(isFavorite: Boolean = true): List<UserTable>

    @Query(
        """
        SELECT * FROM user_table AS ut
                INNER JOIN profession_table AS pt
                ON ut.profession = pt.profession_id
                WHERE firstName LIKE :filter
    """
    )
    fun search(filter: String): List<UserProfession>

    @Query(
        """
            SELECT * FROM user_table LIMIT :count OFFSET :offset
        """
    )
    fun paginatedUsers(count: Int, offset: Int): List<UserTable>
}