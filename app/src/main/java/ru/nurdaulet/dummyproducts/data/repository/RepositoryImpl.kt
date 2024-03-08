package ru.nurdaulet.dummyproducts.data.repository

import ru.nurdaulet.dummyproducts.domain.Repository
import ru.nurdaulet.dummyproducts.domain.models.Product
import javax.inject.Inject

class RepositoryImpl @Inject constructor() : Repository {

    override suspend fun getProducts(
        skip: Int,
        onSuccess: (List<Product>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ): List<Product> {
        TODO("Not yet implemented")
    }
}