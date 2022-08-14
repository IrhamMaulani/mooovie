package com.shiinasoftware.mooovie.repositories

import com.shiinasoftware.mooovie.network.ApiService
import com.shiinasoftware.mooovie.network.retrofitService

class GenreRepository {
    private var services: ApiService = retrofitService

    suspend fun getGenre() = services.getGenresAsync().await()
}