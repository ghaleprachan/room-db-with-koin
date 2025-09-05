package app.prgghale.roomdb.app.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.prgghale.roomdb.di.appModule
import app.prgghale.roomdb.di.commonDbModule
import app.prgghale.roomdb.di.platformDBModule
import app.prgghale.roomdb.di.repositoryModule
import app.prgghale.roomdb.di.viewModelModule
import app.prgghale.roomdb.ui.App
import org.koin.core.context.GlobalContext.startKoin

fun main(args: Array<String>) {
    application {
        initKoin()
        Window(
            title = "SimpleNews",
            onCloseRequest = {
                exitApplication()
            },
//            onCloseRequest = ::exitApplication,
        ) {
            App()
        }
    }
}

private fun initKoin() {
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
}

