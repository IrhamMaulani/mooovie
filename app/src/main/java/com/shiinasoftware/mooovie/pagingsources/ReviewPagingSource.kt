package com.shiinasoftware.mooovie.pagingsources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shiinasoftware.mooovie.models.Movie
import com.shiinasoftware.mooovie.models.Review
import com.shiinasoftware.mooovie.network.retrofitService
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class ReviewPagingSource(private val movieId: String) : PagingSource<Int, Review>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val nextPageNumber = params.key ?: STARTING_PAGE_INDEX

            val response =
                retrofitService.getReviewsAsync(page = nextPageNumber, movieId = movieId).await()
            val reviews = response.results
            LoadResult.Page(
                data = reviews,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < reviews.size) nextPageNumber + 1 else null
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
