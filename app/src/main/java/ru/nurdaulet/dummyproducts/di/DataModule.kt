package ru.nurdaulet.dummyproducts.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import fm.sok.app.di.ApplicationScope
import ru.nurdaulet.dummyproducts.data.db.AppDatabase
import ru.nurdaulet.dummyproducts.data.db.ProductsDao
import ru.nurdaulet.dummyproducts.data.repository.RepositoryImpl
import ru.nurdaulet.dummyproducts.domain.Repository

@Module
interface DataModule {

    @Binds
    fun bindContext(application: Application): Context

    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    companion object {

        @ApplicationScope
        @Provides
        fun providesNewsDao(
            application: Application
        ): ProductsDao {
            return AppDatabase.getInstance(application).productsDao()
        }
    }

}