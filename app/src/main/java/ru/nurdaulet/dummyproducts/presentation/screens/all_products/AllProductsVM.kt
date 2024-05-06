package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import kotlinx.coroutines.launch
import ru.nurdaulet.dummyproducts.application.DummyApplication
import ru.nurdaulet.dummyproducts.data.repository.ProductsListDataSource
import ru.nurdaulet.dummyproducts.domain.Repository
import ru.nurdaulet.dummyproducts.domain.models.Product
import ru.nurdaulet.dummyproducts.utils.Constants.LIMIT
import ru.nurdaulet.dummyproducts.utils.Constants.NO_NETWORK
import ru.nurdaulet.dummyproducts.utils.Resource
import java.io.IOException
import javax.inject.Inject

class AllProductsVM @Inject constructor(
    app: Application,
    private val repository: Repository
) : AndroidViewModel(app) {

    private var _products: MutableLiveData<Resource<List<Product>>> = MutableLiveData()
    val products: LiveData<Resource<List<Product>>> get() = _products

    var productsOffset = 0
    var productsResponse: MutableList<Product>? = null

    fun getProducts() = viewModelScope.launch {
        _products.value = Resource.Loading()
        try {
            if (hasInternetConnection) {
                repository.getProducts(productsOffset,
                    onSuccess = { data ->
                        if (data.isNotEmpty()) {
                            if (productsResponse == null) {
                                productsResponse = data.toMutableList()
                            } else {
                                val oldData = productsResponse
                                val newData = data
                                oldData?.addAll(newData)
                            }
                            _products.value = Resource.Success(productsResponse ?: data)
                        }
                    }, onFailure = {
                        _products.value = Resource.Error(it)
                    })
            } else {
                _products.value = Resource.Error(NO_NETWORK)
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _products.value = Resource.Error(t.message)
                else -> _products.value = Resource.Error(t.message)
            }
        }
    }

    fun getProductsPaging() = Pager(
        config = PagingConfig(pageSize = LIMIT, enablePlaceholders = false),
        pagingSourceFactory = { ProductsListDataSource(repository) }
    ).liveData

    private val hasInternetConnection = (app as DummyApplication).hasInternetConnection()
}