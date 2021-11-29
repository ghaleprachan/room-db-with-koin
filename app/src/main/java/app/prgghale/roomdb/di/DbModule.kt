package app.prgghale.roomdb.di

import android.content.Context
import androidx.room.Room
import app.prgghale.roomdb.data.dao.UsersDao
import app.prgghale.roomdb.data.database.AppDatabase
import app.prgghale.roomdb.data.database.MIGRATION_1_2
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val DbName = "RoomDbName"
val dbModule = module {
    single { provideRoomDatabase(androidContext()) }
    single { provideUserDao(get()) }
}

private fun provideRoomDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, DbName)
        .addMigrations(MIGRATION_1_2)
        .fallbackToDestructiveMigration()
        .build()
}

private fun provideUserDao(appDatabase: AppDatabase): UsersDao {
    return appDatabase.getUsersDao()
}