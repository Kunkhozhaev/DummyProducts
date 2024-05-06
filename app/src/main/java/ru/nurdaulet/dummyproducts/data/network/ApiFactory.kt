package ru.nurdaulet.dummyproducts.data.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nurdaulet.dummyproducts.application.DummyApplication
import ru.nurdaulet.dummyproducts.utils.Constants.BASE_URL

object ApiFactory {

    private val retrofit by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(DummyApplication.getApplicationContext()!!))
            .build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val apiService: DummyApi by lazy {
        retrofit.create(DummyApi::class.java)
    }
}
