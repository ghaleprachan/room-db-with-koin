package app.prgghale.roomdb.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import java.io.File

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), DbName)
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    )
}

//actual object AppDatabaseConstructor :
//    RoomDatabaseConstructor<AppDatabase> {
//    actual override fun initialize(): AppDatabase {
//        TODO("Not yet implemented")
//    }
//}