package ru.nurdaulet.dummyproducts.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nurdaulet.dummyproducts.utils.Constants.BASE_URL

object ApiFactory {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: DummyApi by lazy {
        retrofit.create(DummyApi::class.java)
    }
}
