package ru.nurdaulet.dummyproducts.application

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import ru.nurdaulet.dummyproducts.di.DaggerApplicationComponent

class DummyApplication : Application() {

    val component by lazy { DaggerApplicationComponent.factory().create(this) }

    fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}