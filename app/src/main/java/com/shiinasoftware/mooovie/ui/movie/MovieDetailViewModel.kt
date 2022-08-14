package com.shiinasoftware.mooovie.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shiinasoftware.mooovie.models.Genre
import com.shiinasoftware.mooovie.models.Movie
import com.shiinasoftware.mooovie.models.apis.ApiStatus
import com.shiinasoftware.mooovie.repositories.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MovieDetailViewModel : ViewModel() {

    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>>
        get() = _genres

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val movieRepository = MovieRepository()

    fun setGenres(genres: List<Genre>) {
        _genres.value = genres
    }

    fun showMovie(movieId: String) {
        coroutineScope.launch {
            val movieResult = movieRepository.showMovie(movieId)
            _status.value = ApiStatus.LOADING
            try {
                _movie.value = movieResult
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