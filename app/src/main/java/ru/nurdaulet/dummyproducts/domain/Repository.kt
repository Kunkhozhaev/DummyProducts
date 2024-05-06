package ru.nurdaulet.dummyproducts.domain

import ru.nurdaulet.dummyproducts.domain.models.Product
import ru.nurdaulet.dummyproducts.utils.Resource

interface Repository {
    suspend fun getProducts(
        skip: Int,
        onSuccess: (List<Product>) -> Unit,
        onFailure: (msg: String?) -> Unit
    )

    suspend fun getProductsPaging(
        skip: Int,
        limit: Int,
        result: (Resource<List<Product>>) -> Unit,
    )
}