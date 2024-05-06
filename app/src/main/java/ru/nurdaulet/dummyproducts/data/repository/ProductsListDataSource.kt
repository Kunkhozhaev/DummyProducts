package ru.nurdaulet.dummyproducts.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.nurdaulet.dummyproducts.domain.Repository
import ru.nurdaulet.dummyproducts.domain.models.Product
import ru.nurdaulet.dummyproducts.utils.Constants.LIMIT
import javax.inject.Inject

class ProductsListDataSource @Inject constructor(
    private val repository: Repository
) : PagingSource<Int, Product>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val position = params.key ?: STARTING_PAGE_INDEX
        var result: LoadResult<Int, Product> = LoadResult.Error(Exception())

        repository.getProducts(position,
            onSuccess = { productList ->
                result = LoadResult.Page(
                    data = productList,
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - LIMIT,
                    nextKey = if (productList.isEmpty()) null else position + LIMIT
                )
            },
            onFailure = {
                result = LoadResult.Error(Exception(it))
            })

        return result
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}