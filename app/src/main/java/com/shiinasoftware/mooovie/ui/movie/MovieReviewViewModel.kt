package com.shiinasoftware.mooovie.ui.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shiinasoftware.mooovie.models.Genre
import com.shiinasoftware.mooovie.models.Movie
import com.shiinasoftware.mooovie.models.Review
import com.shiinasoftware.mooovie.models.apis.ApiStatus
import com.shiinasoftware.mooovie.repositories.MovieRepository
import com.shiinasoftware.mooovie.repositories.ReviewRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MovieReviewViewModel : ViewModel() {
    private val _reviews = MutableStateFlow<PagingData<Review>>(PagingData.empty())
    val reviews: StateFlow<PagingData<Review>>
        get() = _reviews

//    private val _reviews = MutableLiveData<List<Review>>()
//    val reviews: LiveData<List<Review>>
//        get() = _reviews

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val reviewRepository = ReviewRepository()

    fun getReviews(movieId: String) {
        coroutineScope.launch {
            val reviewResult = reviewRepository.getReviews(movieId).cachedIn(viewModelScope)
            _status.value = ApiStatus.LOADING
            try {
                reviewResult.collectLatest {
                    _reviews.value = it
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

//    fun getReviews(movieId: String) {
//        coroutineScope.launch {
//            val reviewResult = reviewRepository.getReviews(movieId)
//            _status.value = ApiStatus.LOADING
//            try {
//                _reviews.value = reviewResult.results
//                _status.value = ApiStatus.SUCCESS
//            } catch (throwable: Throwable) {
//                when (throwable) {
//                    is HttpException -> {
//                        _status.postValue(ApiStatus.ERROR)
//                    }
//                }
//            }
//        }
//    }
}