package app.prgghale.roomdb.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.dsl.module

/**
 * @author[Prachan Ghale] Updated on 1-25-2022
 * */
val appModule = module {
    single { Dispatchers.Default }
    single { CoroutineScope(Dispatchers.Main + Job()) }
}