package app.prgghale.roomdb.di

import android.content.Context
import app.prgghale.roomdb.data.dao.ProfessionDao
import app.prgghale.roomdb.data.dao.UsersDao
import app.prgghale.roomdb.data.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
val dbModule = module {
    single { provideRoomDatabase(androidContext(), get()) }//FIXME !!Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
    single { provideUserDao(get()) }
    single { provideProfessionDao(get()) }
}
private fun provideRoomDatabase(context: Context, scope: CoroutineScope): AppDatabase {
    //FIXME CoroutineScope inner AppDatabase is better
    return AppDatabase.getInstance(context,scope)
}

private fun provideUserDao(appDatabase: AppDatabase): UsersDao {
    return appDatabase.getUsersDao()
}

private fun provideProfessionDao(appDatabase: AppDatabase): ProfessionDao {
    return appDatabase.getProfessionDao()
}