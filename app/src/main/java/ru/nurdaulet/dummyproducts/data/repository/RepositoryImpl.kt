package ru.nurdaulet.dummyproducts.data.repository

import ru.nurdaulet.dummyproducts.data.Mapper
import ru.nurdaulet.dummyproducts.data.db.ProductsDao
import ru.nurdaulet.dummyproducts.data.network.ApiFactory
import ru.nurdaulet.dummyproducts.domain.Repository
import ru.nurdaulet.dummyproducts.domain.models.Product
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val productsDao: ProductsDao,
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
                    //TODO Clear database
//                    productsDao.insertProductList(mapper.mapListDtoToDbModel(products))
//                    val productsDbList = productsDao.getProducts(skip)
                    onSuccess.invoke(mapper.mapListDtoToModel(products))
                }
            }
        } else {
            onFailure.invoke(response.errorBody()?.string())
        }
    }
}