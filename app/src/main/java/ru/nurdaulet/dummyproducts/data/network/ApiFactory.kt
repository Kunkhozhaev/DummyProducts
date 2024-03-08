package ru.nurdaulet.dummyproducts.data.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nurdaulet.dummyproducts.application.DummyApplication
import ru.nurdaulet.dummyproducts.utils.Constants.BASE_URL

object ApiFactory {

    private val retrofit by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(ChuckerInterceptor(DummyApplication.getApplicationContext()!!))
            .addInterceptor { chain ->
                val request = chain.request()
                val url = request.url.newBuilder()
                    //.addQueryParameter("apiKey", API_KEY)
                    .build()
                chain.proceed(request.newBuilder().url(url).build())
            }
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
