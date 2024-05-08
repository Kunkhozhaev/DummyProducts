package ru.nurdaulet.dummyproducts.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.nurdaulet.dummyproducts.data.network.model.ProductsListDto
import ru.nurdaulet.dummyproducts.utils.Constants.PAGE_SIZE

interface DummyApi {
    @GET("products")
    suspend fun getProducts(
        @Query(QUERY_PARAM_SKIP)
        skip: Int,
        @Query(QUERY_PARAM_LIMIT)
        limit: Int = PAGE_SIZE
    ): Response<ProductsListDto>

    companion object {
        const val QUERY_PARAM_SKIP = "skip"
        const val QUERY_PARAM_LIMIT = "limit"
    }
}