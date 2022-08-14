package com.shiinasoftware.mooovie.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    @Json(name = "id")
    val id: String = "",
    @Json(name = "name")
    val name: String = ""
) : Parcelable
