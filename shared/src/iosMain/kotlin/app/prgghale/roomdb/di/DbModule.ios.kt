package app.prgghale.roomdb.di

import androidx.room.RoomDatabase
import app.prgghale.roomdb.data.database.AppDatabase
import app.prgghale.roomdb.data.database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDBModule: Module= module {
    single<RoomDatabase.Builder<AppDatabase>> {
        provideDatabaseBuilder()
    }
}

private fun provideDatabaseBuilder( ): RoomDatabase.Builder<AppDatabase> {
    return getDatabaseBuilder( )
}