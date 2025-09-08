package app.prgghale.roomdb.data.table

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.benasher44.uuid.uuid4

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
    val id: String = uuid4().toString(),
    val firstName: String?,
    val lastName: String?,
    val address: String?,
    val profession: String?,
    val isFavorite: Boolean = false
) {
    fun displayName(): String = "$firstName $lastName"
}