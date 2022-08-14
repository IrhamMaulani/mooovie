package com.shiinasoftware.mooovie.models.apis

import com.squareup.moshi.Json

data class ApiResponse<T>(@Json(name = "results") var results: List<T>)