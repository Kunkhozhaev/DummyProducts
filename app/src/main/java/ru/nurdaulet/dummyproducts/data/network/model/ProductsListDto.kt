package ru.nurdaulet.dummyproducts.data.network.model

data class ProductsListDto(
    val products: List<ProductDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
