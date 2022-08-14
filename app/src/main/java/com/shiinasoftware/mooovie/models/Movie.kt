package com.shiinasoftware.mooovie.models

import com.shiinasoftware.mooovie.models.apis.ApiResponse
import com.shiinasoftware.mooovie.network.BaseURL.ORIGINAL_IMAGE_URL
import com.shiinasoftware.mooovie.network.BaseURL.SMALL_IMAGE_URL
import com.shiinasoftware.mooovie.ui.shared.utils.convertDateStringToFullDate
import com.squareup.moshi.Json

data class Movie(
    @Json(name = "id")
    val id: String = "",
    @Json(name = "title")
    val title: String = "",
    @Json(name = "poster_path")
    val posterPath: String? = "",
    @Json(name = "vote_average")
    val voteAverage: Double = 0.0,
    @Json(name = "genres")
    val genres: List<Genre>?,
    @Json(name = "overview")
    val overview: String? = "",
    @Json(name = "release_date")
    val releaseDate: String? = "",
    @Json(name = "videos")
    val videos: ApiResponse<Video>?,
) {
    val smallImage: String = "$SMALL_IMAGE_URL/${this.posterPath}"
    val originalImage: String = "$ORIGINAL_IMAGE_URL/${this.posterPath}"
    val fullReleaseDate: String = convertDateStringToFullDate(this.releaseDate ?: "")
    val roundVoteAverage: String = String.format("%.1f", this.voteAverage)
    val trailerKey: String =
        if (this.videos?.results?.isNotEmpty() == true) this.videos.results.first { it.isTrailer }.key else "ubFq-wV3Eic"
}