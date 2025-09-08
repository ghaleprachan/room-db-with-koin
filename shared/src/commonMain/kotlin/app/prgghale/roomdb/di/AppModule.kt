package app.prgghale.roomdb.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.dsl.module
import kotlinx.coroutines.IO
/**
 * @author[Prachan Ghale] Updated on 1-25-2022
 * */
val appModule = module {
    single { Dispatchers.Default }
    single { CoroutineScope(Dispatchers.IO + Job()) }//FIXME Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
}