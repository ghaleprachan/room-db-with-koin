package app.prgghale.roomdb

import android.app.Application
import app.prgghale.roomdb.di.appModule
import app.prgghale.roomdb.di.commonDbModule
import app.prgghale.roomdb.di.platformDBModule
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
                    commonDbModule,
                    platformDBModule,
                    viewModelModule,
                    repositoryModule,
                    appModule,
                )
            )
            androidLogger()
        }
    }
}