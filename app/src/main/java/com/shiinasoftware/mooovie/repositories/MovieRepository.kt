package com.shiinasoftware.mooovie.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.shiinasoftware.mooovie.network.retrofitService
import com.shiinasoftware.mooovie.pagingsources.MoviePagingSource

class MovieRepository {
    fun getMovies(genreId: String) =
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { MoviePagingSource(genreId) }).flow

    suspend fun showMovie(movieId: String) = retrofitService.showMovieAsync(movieId).await()
}