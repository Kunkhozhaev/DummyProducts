package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import ru.nurdaulet.dummyproducts.domain.Repository
import javax.inject.Inject

class AllProductsVM @Inject constructor(
    app: Application,
    private val repository: Repository
) : AndroidViewModel(app) {
    fun getProductsPaging() = repository.getDataSource().cachedIn(viewModelScope)
}