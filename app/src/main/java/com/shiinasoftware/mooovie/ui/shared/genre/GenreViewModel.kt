package com.shiinasoftware.mooovie.ui.shared.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shiinasoftware.mooovie.models.Genre
import com.shiinasoftware.mooovie.models.apis.ApiStatus
import com.shiinasoftware.mooovie.repositories.GenreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException

class GenreViewModel : ViewModel() {

    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>>
        get() = _genres

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val genreRepository = GenreRepository()

    init {
        getGenres()
    }

    fun getGenres() {
        coroutineScope.launch {
            val genreResult = genreRepository.getGenre()
            _status.value = ApiStatus.LOADING
            try {

                _genres.value = genreResult.genres
                _status.value = ApiStatus.SUCCESS
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        _status.postValue(ApiStatus.ERROR)
                    }
                }
            }
        }
    }
}