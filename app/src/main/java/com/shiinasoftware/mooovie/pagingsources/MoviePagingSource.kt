package com.shiinasoftware.mooovie.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shiinasoftware.mooovie.models.Movie
import com.shiinasoftware.mooovie.network.retrofitService
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MoviePagingSource (private val genreId: String) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPageNumber = params.key ?: STARTING_PAGE_INDEX
            val response =
                retrofitService.getMoviesAsync(nextPageNumber, genreId = genreId).await()
            val movies = response.results
            LoadResult.Page(
                data = movies,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.results.size) nextPageNumber + 1 else null
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
