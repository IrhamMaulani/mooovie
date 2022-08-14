package com.shiinasoftware.mooovie.models.apis

import com.shiinasoftware.mooovie.models.Genre
import com.squareup.moshi.Json

data class GenreResponse(
    @Json(name = "genres")
    val genres: List<Genre>
)
