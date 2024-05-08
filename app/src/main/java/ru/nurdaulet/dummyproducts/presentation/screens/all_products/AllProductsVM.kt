package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import ru.nurdaulet.dummyproducts.domain.Repository
import javax.inject.Inject

class AllProductsVM @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun getProductsPaging() = repository.getDataSource().cachedIn(viewModelScope)
}