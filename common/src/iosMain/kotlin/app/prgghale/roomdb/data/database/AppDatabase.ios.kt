package app.prgghale.roomdb.data.database


import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = documentDirectory() + DbName
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
    )
}
@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}

//actual object AppDatabaseConstructor :
//    RoomDatabaseConstructor<AppDatabase> {
//    actual override fun initialize(): AppDatabase {
//        TODO("Not yet implemented")
//    }
//}