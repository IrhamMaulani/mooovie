package com.shiinasoftware.mooovie.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shiinasoftware.mooovie.models.Genre

class MovieTrailerViewModel : ViewModel() {
    private val _key = MutableLiveData<String>()
    val key: LiveData<String>
        get() = _key

    fun setKey(key: String) {
        _key.value = key
    }
}