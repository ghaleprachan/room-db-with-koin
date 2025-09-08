package app.prgghale.roomdb.data.dao

import androidx.room.Dao
import androidx.room.Query
import app.prgghale.roomdb.data.table.ProfessionTable

@Dao
interface ProfessionDao : BaseDao<ProfessionTable> {
    @Query("SELECT * FROM profession_table")
    suspend fun getAllProfessions(): List<ProfessionTable>
}