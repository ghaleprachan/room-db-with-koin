package app.prgghale.roomdb.di

import app.prgghale.roomdb.data.repository.UserRepoImpl
import app.prgghale.roomdb.data.repository.UserRepository
import app.prgghale.roomdb.ui.loading.observers.ProfessionObserver
import app.prgghale.roomdb.ui.loading.observers.UserObserver
import app.prgghale.roomdb.ui.loading.observers.UserProfessionObserver
import app.prgghale.roomdb.ui.search.SearchUser
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepoImpl(get(), get()) }

    single { SearchUser(get(), get(), get()) }
    single { UserObserver(get()) }
    single { ProfessionObserver(get()) }
    single { UserProfessionObserver(get()) }
}