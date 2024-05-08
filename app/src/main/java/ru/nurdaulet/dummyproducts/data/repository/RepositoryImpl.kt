package ru.nurdaulet.dummyproducts.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.nurdaulet.dummyproducts.data.Mapper
import ru.nurdaulet.dummyproducts.domain.Repository
import ru.nurdaulet.dummyproducts.domain.models.Product
import ru.nurdaulet.dummyproducts.utils.Constants
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val mapper: Mapper,
) : Repository {

    override fun getDataSource(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.LIMIT, enablePlaceholders = false),
            pagingSourceFactory = { ProductsListDataSource(mapper) }
        ).flow
    }
}