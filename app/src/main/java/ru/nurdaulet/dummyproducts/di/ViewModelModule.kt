package ru.nurdaulet.dummyproducts.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.nurdaulet.dummyproducts.presentation.screens.all_products.AllProductsVM

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AllProductsVM::class)
    fun bindAllProductsVM(viewModel: AllProductsVM): ViewModel
}