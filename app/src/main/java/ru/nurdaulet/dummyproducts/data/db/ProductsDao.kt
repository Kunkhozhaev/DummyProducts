package ru.nurdaulet.dummyproducts.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductsDao {
//    @Query("SELECT * FROM products_table WHERE id = :id LIMIT 1")
//    suspend fun getTitle(id: Long): ProductDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductList(productList: List<ProductDbModel>)

//    @Query("SELECT * FROM titles WHERE isReadyToBeShown = 1 LIMIT 5")
//    fun getInitialTitlesFeed(): LiveData<List<ProductDbModel>>

    @Query("SELECT * FROM products_table WHERE id <= :pos + 20 ORDER BY id ASC")
    suspend fun getProducts(pos: Int): List<ProductDbModel>
}