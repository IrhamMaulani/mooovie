package com.shiinasoftware.mooovie.models

import com.squareup.moshi.Json

data class Review(
    @Json(name = "id")
    val id: String? = "",
    @Json(name = "author")
    val author: String? = "",
    @Json(name = "content")
    val content: String? = "",
    @Json(name = "author_details")
    val authorDetail: AuthorDetail?
)