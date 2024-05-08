package ru.nurdaulet.dummyproducts.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.nurdaulet.dummyproducts.presentation.screens.all_products.AllProductsFragment

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: AllProductsFragment)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}