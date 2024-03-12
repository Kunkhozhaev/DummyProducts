package ru.nurdaulet.dummyproducts.data

import ru.nurdaulet.dummyproducts.data.network.model.ProductDto
import ru.nurdaulet.dummyproducts.domain.models.Product
import javax.inject.Inject

class Mapper @Inject constructor() {

    private fun mapDtoToModel(product: ProductDto): Product {
        return Product(
            id = product.id,
            title = product.title,
            description = product.description,
            price = product.price,
            discountPercentage = product.discountPercentage,
            rating = product.rating,
            stock = product.stock,
            brand = product.brand,
            category = product.category,
            thumbnail = product.thumbnail,
            images = product.images
        )
    }

    fun mapListDtoToModel(list: List<ProductDto>): List<Product> = list.map {
        mapDtoToModel(it)
    }
}