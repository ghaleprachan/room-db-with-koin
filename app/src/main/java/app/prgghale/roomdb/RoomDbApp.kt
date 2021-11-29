package app.prgghale.roomdb

import android.app.Application
import app.prgghale.roomdb.di.dbModule
import app.prgghale.roomdb.di.repositoryModule
import app.prgghale.roomdb.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RoomDbApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RoomDbApp)
            modules(
                listOf(
                    dbModule,
                    viewModelModule,
                    repositoryModule,
                )
            )
            androidLogger()
        }
    }
}