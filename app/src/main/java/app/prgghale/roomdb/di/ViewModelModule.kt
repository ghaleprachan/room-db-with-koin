package app.prgghale.roomdb.di

import app.prgghale.roomdb.ui.home.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { UserViewModel(get()) }
}