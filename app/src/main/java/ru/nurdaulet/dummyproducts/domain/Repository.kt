package ru.nurdaulet.dummyproducts.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.nurdaulet.dummyproducts.domain.models.Product

interface Repository {
    fun getDataSource(): Flow<PagingData<Product>>
}