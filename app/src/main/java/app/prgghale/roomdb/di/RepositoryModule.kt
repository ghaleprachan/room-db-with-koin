package app.prgghale.roomdb.di

import app.prgghale.roomdb.data.repository.UserRepoImpl
import app.prgghale.roomdb.data.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepoImpl(get(), get()) }
}