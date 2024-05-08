package ru.nurdaulet.dummyproducts.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.nurdaulet.dummyproducts.data.Mapper
import ru.nurdaulet.dummyproducts.data.network.ApiFactory
import ru.nurdaulet.dummyproducts.domain.models.Product
import ru.nurdaulet.dummyproducts.utils.Constants.PAGE_SIZE
import java.io.IOException
import javax.inject.Inject

class ProductsListDataSource @Inject constructor(
    private val mapper: Mapper,
) : PagingSource<Int, Product>() {

    private val apiService = ApiFactory.apiService

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> =
        try {
            val position = params.key ?: STARTING_PAGE_INDEX
            var result: LoadResult<Int, Product> = LoadResult.Error(Exception())

            val response = apiService.getProducts(position)
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    val products = data.products
                    if (products.isNotEmpty()) {
                        val productList = mapper.mapListDtoToModel(products)
                        result = LoadResult.Page(
                            data = productList,
                            prevKey = if (position == STARTING_PAGE_INDEX) null else position - PAGE_SIZE,
                            nextKey = if (productList.isEmpty()) null else position + PAGE_SIZE
                        )
                    }
                }
            } else {
                result = LoadResult.Error(Exception(response.errorBody()?.string()))
            }
            result
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (ignore: Exception) {
            LoadResult.Error(ignore)
        }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}