package ru.nurdaulet.dummyproducts.data.repository

import ru.nurdaulet.dummyproducts.data.Mapper
import ru.nurdaulet.dummyproducts.data.network.ApiFactory
import ru.nurdaulet.dummyproducts.domain.Repository
import ru.nurdaulet.dummyproducts.domain.models.Product
import ru.nurdaulet.dummyproducts.utils.Resource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val mapper: Mapper,
) : Repository {
    private val apiService = ApiFactory.apiService

    override suspend fun getProducts(
        skip: Int,
        onSuccess: (List<Product>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val response = apiService.getProducts(skip)
        if (response.isSuccessful) {
            response.body()?.let { data ->
                val products = data.products
                if (products.isNotEmpty()) {
                    onSuccess.invoke(mapper.mapListDtoToModel(products))
                }
            }
        } else {
            onFailure.invoke(response.errorBody()?.string())
        }
    }

    override suspend fun getProductsPaging(skip: Int, limit: Int, result: (Resource<List<Product>>) -> Unit) {
        val response = apiService.getProductsPaging(skip, limit)
        if (response.isSuccessful) {
            response.body()?.let { data ->
                val products = data.products
                if (products.isNotEmpty()) {
                    result.invoke(Resource.Success(mapper.mapListDtoToModel(products)))
                }
            }
        } else {
            result.invoke(Resource.Error(response.errorBody()?.string()))
        }
    }
}