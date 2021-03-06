package app.prgghale.roomdb.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.prgghale.roomdb.data.dao.ProfessionDao
import app.prgghale.roomdb.data.dao.UsersDao
import app.prgghale.roomdb.data.database.AppDatabase
import app.prgghale.roomdb.data.database.MIGRATION_1_2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val DbName = "RoomDbName"
val dbModule = module {
    single { provideRoomDatabase(androidContext(), get()) }
    single { provideUserDao(get()) }
    single { provideProfessionDao(get()) }
}


fun provideRoomDatabase(context: Context, scope: CoroutineScope): AppDatabase {
    var database: AppDatabase? = null
    database = Room.databaseBuilder(context, AppDatabase::class.java, DbName)
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                AppDatabase.onCreate(scope = scope, database = database)
            }
        })
        .addMigrations(MIGRATION_1_2)
        .fallbackToDestructiveMigration()
        .build()
    return database
}

private fun provideUserDao(appDatabase: AppDatabase): UsersDao {
    return appDatabase.getUsersDao()
}

private fun provideProfessionDao(appDatabase: AppDatabase): ProfessionDao {
    return appDatabase.getProfessionDao()
}