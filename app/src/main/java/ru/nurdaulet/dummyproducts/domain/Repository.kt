package ru.nurdaulet.dummyproducts.domain

import ru.nurdaulet.dummyproducts.domain.models.Product

interface Repository {
    suspend fun getProducts(
        skip: Int,
        onSuccess: (List<Product>) -> Unit,
        onFailure: (msg: String?) -> Unit
    )
}