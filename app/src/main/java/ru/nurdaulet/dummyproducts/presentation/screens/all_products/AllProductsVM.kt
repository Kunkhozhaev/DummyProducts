package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.nurdaulet.dummyproducts.application.DummyApplication
import ru.nurdaulet.dummyproducts.domain.Repository
import ru.nurdaulet.dummyproducts.domain.models.Product
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

    fun getProducts(skip: Int) = viewModelScope.launch {
        _products.value = Resource.Loading()
        try {
            if (hasInternetConnection()) {
                repository.getProducts(skip,
                    onSuccess = {
                        _products.value = Resource.Success(it)

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

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<DummyApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}