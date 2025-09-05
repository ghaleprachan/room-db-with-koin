package app.prgghale.roomdb.di

import android.content.Context
import androidx.room.RoomDatabase
import app.prgghale.roomdb.data.database.AppDatabase
import app.prgghale.roomdb.data.database.getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDBModule: Module= module {
    single<RoomDatabase.Builder<AppDatabase>> {
        provideDatabaseBuilder(androidContext())
    }
}

private fun provideDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDatabase> {
    return getDatabaseBuilder(ctx)
}