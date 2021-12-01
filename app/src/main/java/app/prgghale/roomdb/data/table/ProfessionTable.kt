package app.prgghale.roomdb.data.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "profession_table")
data class ProfessionTable(
    @PrimaryKey
    @ColumnInfo(name = "profession_id")
    var professionId: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "profession_name")
    var professionName: String? = ""
)