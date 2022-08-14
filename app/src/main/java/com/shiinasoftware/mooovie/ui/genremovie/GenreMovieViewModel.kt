package com.shiinasoftware.mooovie.ui.genremovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shiinasoftware.mooovie.models.apis.ApiStatus

class GenreMovieViewModel : ViewModel() {
    private val _backToHome = MutableLiveData<Boolean>()
    val backToHome: LiveData<Boolean>
        get() = _backToHome

    fun backToHome() {
        _backToHome.value = true
    }
}