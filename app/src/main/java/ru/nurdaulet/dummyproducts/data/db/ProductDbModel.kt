package ru.nurdaulet.dummyproducts.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.nurdaulet.dummyproducts.utils.Constants.PRODUCTS_TABLE

@Entity(tableName = PRODUCTS_TABLE)
data class ProductDbModel(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: String
)