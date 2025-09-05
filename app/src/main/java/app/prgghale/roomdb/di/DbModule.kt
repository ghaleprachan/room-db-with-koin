package app.prgghale.roomdb.di

import android.content.Context
import androidx.room.RoomDatabase
import app.prgghale.roomdb.data.dao.ProfessionDao
import app.prgghale.roomdb.data.dao.UsersDao
import app.prgghale.roomdb.data.database.AppDatabase
import app.prgghale.roomdb.data.database.getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
val commonDbModule = module {
    single { provideRoomDatabase(get()) }//FIXME !!Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
    single { provideUserDao(get()) }
    single { provideProfessionDao(get()) }
}

val platformDBModule : Module = module {
    single<RoomDatabase.Builder<AppDatabase>> {
        provideDatabaseBuilder(androidContext())
    }
}

private fun provideDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDatabase> {
    return getDatabaseBuilder(ctx)
}
private fun provideRoomDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
    //FIXME CoroutineScope inner AppDatabase is better
    return AppDatabase.getInstance(builder)
}

private fun provideUserDao(appDatabase: AppDatabase): UsersDao {
    return appDatabase.getUsersDao()
}

private fun provideProfessionDao(appDatabase: AppDatabase): ProfessionDao {
    return appDatabase.getProfessionDao()
}