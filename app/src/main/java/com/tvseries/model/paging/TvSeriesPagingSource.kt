package com.tvseries.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tvseries.model.IDataFactory
import com.tvseries.model.TvSeries
import java.io.IOException
import java.net.HttpRetryException

const val startingKey = 0

class TvSeriesPagingSource(private val service: IDataFactory) : PagingSource<Int, TvSeries>() {
    override fun getRefreshKey(state: PagingState<Int, TvSeries>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvSeries> {
        return try {
            val nextPageNumber = params.key ?: startingKey
            val response = service.getListTvSeries(nextPageNumber)
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpRetryException) {
            LoadResult.Error(e)
        }
    }
}