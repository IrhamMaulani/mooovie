package com.shiinasoftware.mooovie.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.shiinasoftware.mooovie.network.retrofitService
import com.shiinasoftware.mooovie.pagingsources.ReviewPagingSource

class ReviewRepository {
    fun getReviews(movieId: String) =
        Pager(
            config = PagingConfig(pageSize = 1),
            pagingSourceFactory = { ReviewPagingSource(movieId) }).flow

//    suspend fun getReviews(movieId: String) = retrofitService.getReviewsAsync(movieId).await()
}