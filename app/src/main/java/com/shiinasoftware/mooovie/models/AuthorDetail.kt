package com.shiinasoftware.mooovie.models

import com.squareup.moshi.Json

data class AuthorDetail(
    @Json(name = "username")
    val username: String? = "",
    @Json(name = "avatar_path")
    val avatarPath: String? = "",
) {
    val avatarPathClean: String = this.avatarPath?.substring(1) ?: ""
}