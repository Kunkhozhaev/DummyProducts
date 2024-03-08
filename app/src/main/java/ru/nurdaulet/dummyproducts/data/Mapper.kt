package ru.nurdaulet.dummyproducts.data

import ru.nurdaulet.dummyproducts.data.db.ProductDbModel
import ru.nurdaulet.dummyproducts.data.network.model.ProductDto
import ru.nurdaulet.dummyproducts.domain.models.Product
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun mapDtoToDbModel(product: ProductDto): ProductDbModel {
        return ProductDbModel(
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
            images = mapImagesListToString(product.images)
        )
    }

    fun mapDbModelToEntity(productDao: ProductDbModel): Product {
        return Product(
            id = productDao.id,
            title = productDao.title,
            description = productDao.description,
            price = productDao.price,
            discountPercentage = productDao.discountPercentage,
            rating = productDao.rating,
            stock = productDao.stock,
            brand = productDao.brand,
            category = productDao.category,
            thumbnail = productDao.thumbnail,
            images = mapStringToImagesList(productDao.images)
        )
    }

    fun mapImagesListToString(imagesList: List<String>): String {
        return imagesList.joinToString(",") {
            it
        }
    }

    fun mapStringToImagesList(images: String): List<String> {
        return images.split(",")
    }

    fun mapListDtoToDbModel(list: List<ProductDto>): List<ProductDbModel> = list.map {
        mapDtoToDbModel(it)
    }
}