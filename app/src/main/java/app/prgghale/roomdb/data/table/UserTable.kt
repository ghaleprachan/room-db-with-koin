package app.prgghale.roomdb.data.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user_table")
data class UserTable(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val firstName: String?,
    val lastName: String?,
    val address: String?,
) {
    fun displayName(): String = "$firstName $lastName"
}