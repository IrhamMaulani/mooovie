package com.shiinasoftware.mooovie.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel() : ViewModel() {
    private val _movieId = MutableLiveData<String>()
    val movieId: LiveData<String>
        get() = _movieId

    fun setMovieId(movieId: String) {
        _movieId.value = movieId
    }

}