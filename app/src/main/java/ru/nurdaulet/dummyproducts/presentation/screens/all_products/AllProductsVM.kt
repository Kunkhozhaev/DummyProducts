package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import ru.nurdaulet.dummyproducts.application.DummyApplication
import ru.nurdaulet.dummyproducts.data.repository.ProductsListDataSource
import ru.nurdaulet.dummyproducts.domain.Repository
import ru.nurdaulet.dummyproducts.utils.Constants.LIMIT
import javax.inject.Inject

class AllProductsVM @Inject constructor(
    app: Application,
    private val repository: Repository
) : AndroidViewModel(app) {

//    fun getProducts() = viewModelScope.launch {
//        try {
//            if (hasInternetConnection) {
//
//            } else {
//                _products.value = Resource.Error(NO_NETWORK)
//            }
//        } catch (t: Throwable) {
//            when (t) {
//                is IOException -> _products.value = Resource.Error(t.message)
//                else -> _products.value = Resource.Error(t.message)
//            }
//        }
//    }
    //TODO Check without internet connection

    fun getProductsPaging() = Pager(
        config = PagingConfig(pageSize = LIMIT, enablePlaceholders = false),
        pagingSourceFactory = { ProductsListDataSource(repository) }
    ).flow.cachedIn(viewModelScope)

    private val hasInternetConnection = (app as DummyApplication).hasInternetConnection()
}