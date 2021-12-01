package app.prgghale.roomdb.data.domain

import androidx.room.Embedded
import app.prgghale.roomdb.data.table.ProfessionTable
import app.prgghale.roomdb.data.table.UserTable

data class UserProfession(
    @Embedded
    val user: UserTable,
    @Embedded
    val profession: ProfessionTable
)