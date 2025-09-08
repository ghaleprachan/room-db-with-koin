package app.prgghale.roomdb

import androidx.compose.ui.window.ComposeUIViewController
import app.prgghale.roomdb.di.appModule
import app.prgghale.roomdb.di.commonDbModule
import app.prgghale.roomdb.di.platformDBModule
import app.prgghale.roomdb.di.repositoryModule
import app.prgghale.roomdb.di.viewModelModule
import app.prgghale.roomdb.ui.App
import org.koin.core.context.startKoin


fun MainViewController() = ComposeUIViewController {
     startKoin {
        modules(
            listOf(
                commonDbModule,
                platformDBModule,
                viewModelModule,
                repositoryModule,
                appModule,
            )
        )
    }
    App()
}