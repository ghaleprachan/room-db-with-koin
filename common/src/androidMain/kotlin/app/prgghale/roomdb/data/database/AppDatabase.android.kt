package app.prgghale.roomdb.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath(DbName)
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}

//actual object AppDatabaseConstructor :
//    RoomDatabaseConstructor<AppDatabase> {
//    actual override fun initialize(): AppDatabase {
//        TODO("Not yet implemented")
//    }
//}