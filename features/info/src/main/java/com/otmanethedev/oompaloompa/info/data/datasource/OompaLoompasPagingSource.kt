package com.otmanethedev.oompaloompa.info.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.otmanethedev.oompaloompa.info.data.models.toDomain
import com.otmanethedev.oompaloompa.info.domain.models.OompaLoompa
import javax.inject.Inject
import okio.IOException
import retrofit2.HttpException

class OompaLoompasPagingSource @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, OompaLoompa>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OompaLoompa> {
        val pageIndex = params.key ?: ONE
        return try {
            val response = remoteDataSource.getOompaLoompasByPage(page = pageIndex)
            val oompaLoompas = response.results.map { it.toDomain() }

            val nextKey = response.current + ONE
            LoadResult.Page(
                data = oompaLoompas,
                prevKey = if (pageIndex == ONE) null else pageIndex,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, OompaLoompa>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(ONE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(ONE)
        }
    }

    companion object {
        private const val ONE = 1
    }
}