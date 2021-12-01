package app.prgghale.roomdb.data.table

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "user_table",
    foreignKeys = [
        ForeignKey(
            entity = ProfessionTable::class,
            parentColumns = ["profession_id"],
            childColumns = ["profession"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class UserTable(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val firstName: String?,
    val lastName: String?,
    val address: String?,
    val profession: String?
) {
    fun displayName(): String = "$firstName $lastName"
}