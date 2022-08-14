package com.shiinasoftware.mooovie.models

import com.shiinasoftware.mooovie.network.BaseURL.ORIGINAL_IMAGE_URL
import com.shiinasoftware.mooovie.network.BaseURL.SMALL_IMAGE_URL
import com.shiinasoftware.mooovie.ui.shared.utils.convertDateStringToFullDate
import com.squareup.moshi.Json
import kotlin.math.round

data class Video(
    @Json(name = "id")
    val id: String = "",
    @Json(name = "site")
    val site: String? = "",
    @Json(name = "key")
    val key: String = "",
    @Json(name = "type")
    val type: String = "",
) {
    val fullLink: String =
        if (this.site == "YouTube") "https://www.youtube.com/watch?v=${this.key}" else ""
    val isTrailer: Boolean = this.type == "Trailer"
}