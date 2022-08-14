package com.shiinasoftware.mooovie.ui.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.shiinasoftware.mooovie.BaseFragmentBinding
import com.shiinasoftware.mooovie.R
import com.shiinasoftware.mooovie.databinding.FragmentMovieReviewBinding
import com.shiinasoftware.mooovie.ui.shared.review.ReviewListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieReviewFragment : BaseFragmentBinding<FragmentMovieReviewBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieReviewBinding =
        FragmentMovieReviewBinding::inflate

    private val movieViewModel: MovieViewModel by lazy {
        ViewModelProvider(requireParentFragment())[MovieViewModel::class.java]
    }

    private lateinit var viewModel: MovieReviewViewModel

    private val movieReviewViewModel: MovieReviewViewModel by lazy {
        ViewModelProvider(this)[MovieReviewViewModel::class.java]
    }

    override fun setupView(binding: FragmentMovieReviewBinding) {
        movieViewModel.movieId.observe(viewLifecycleOwner) {
            movieReviewViewModel.getReviews(it)
        }


        val reviewListAdapter = ReviewListAdapter()
        showReviewRecycler(reviewListAdapter)

        lifecycle.coroutineScope.launch {

            movieReviewViewModel.reviews.collectLatest {
                reviewListAdapter.submitData(lifecycle, it)
            }
        }

    }

    private fun showReviewRecycler(reviewListAdapter: ReviewListAdapter) {
        val reviewRecycler = binding.rvReview
//        reviewRecycler.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        reviewRecycler.adapter = reviewListAdapter
    }


}