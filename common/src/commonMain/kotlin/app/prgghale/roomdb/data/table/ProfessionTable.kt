package app.prgghale.roomdb.data.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.benasher44.uuid.uuid4

@Entity(tableName = "profession_table")
data class ProfessionTable(
    @PrimaryKey
    @ColumnInfo(name = "profession_id")
    var professionId: String = uuid4().toString(),
    @ColumnInfo(name = "profession_name")
    var professionName: String? = ""
)