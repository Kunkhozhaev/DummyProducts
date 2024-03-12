package ru.nurdaulet.dummyproducts.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import ru.nurdaulet.dummyproducts.data.repository.RepositoryImpl
import ru.nurdaulet.dummyproducts.domain.Repository

@Module
interface DataModule {

    @Binds
    fun bindContext(application: Application): Context

    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

}