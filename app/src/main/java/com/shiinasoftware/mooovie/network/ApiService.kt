package com.shiinasoftware.mooovie.network


import com.shiinasoftware.mooovie.models.Movie
import com.shiinasoftware.mooovie.models.Review
import com.shiinasoftware.mooovie.models.apis.ApiResponse
import com.shiinasoftware.mooovie.models.apis.GenreResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("genre/movie/list")
    fun getGenresAsync(): Deferred<GenreResponse>

    @GET("discover/movie")
    fun getMoviesAsync(
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String = ""
    ): Deferred<ApiResponse<Movie>>

    @GET("movie/{movieId}?append_to_response=videos")
    fun showMovieAsync(@Path("movieId") id: String): Deferred<Movie>

    @GET("movie/{movieId}/reviews")
    fun getReviewsAsync(
        @Path("movieId") movieId: String,
        @Query("page") page: Int = 1,
    ): Deferred<ApiResponse<Review>>
}