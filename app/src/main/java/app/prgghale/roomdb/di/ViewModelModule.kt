package app.prgghale.roomdb.di

import app.prgghale.roomdb.ui.home.UserViewModel
import app.prgghale.roomdb.ui.loading.LoadingViewModel
import app.prgghale.roomdb.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { UserViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { LoadingViewModel(get(), get(), get()) }
}