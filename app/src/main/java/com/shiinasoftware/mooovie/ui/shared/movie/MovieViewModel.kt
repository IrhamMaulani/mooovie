package com.shiinasoftware.mooovie.ui.shared.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shiinasoftware.mooovie.models.Movie
import com.shiinasoftware.mooovie.models.apis.ApiStatus
import com.shiinasoftware.mooovie.repositories.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MovieViewModel : ViewModel() {

    private val _movies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movies: StateFlow<PagingData<Movie>>
        get() = _movies

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val movieRepository = MovieRepository()

    fun getMovies(genreId: String = "") {
        coroutineScope.launch {
            val movieResult = movieRepository.getMovies(genreId).cachedIn(viewModelScope)
            _status.value = ApiStatus.LOADING
            try {
                movieResult.collectLatest {
                    _movies.value = it
                }
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