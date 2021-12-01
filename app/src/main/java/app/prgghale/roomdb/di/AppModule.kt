package app.prgghale.roomdb.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.dsl.module

val appModule = module {
    single { Dispatchers.Default }
    single { CoroutineScope(Dispatchers.Main + Job()) }
}