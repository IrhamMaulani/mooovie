package com.shiinasoftware.mooovie.ui.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.shiinasoftware.mooovie.BaseFragmentBinding
import com.shiinasoftware.mooovie.R
import com.shiinasoftware.mooovie.databinding.FragmentMovieBinding
import com.shiinasoftware.mooovie.models.Movie

class MovieFragment : BaseFragmentBinding<FragmentMovieBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieBinding =
        FragmentMovieBinding::inflate

    private val movieViewModel: MovieViewModel by lazy {
        ViewModelProvider(this)[MovieViewModel::class.java]
    }

    private val movieDetailViewModel: MovieDetailViewModel by lazy {
        ViewModelProvider(this)[MovieDetailViewModel::class.java]
    }

    private val movieTrailerViewModel: MovieTrailerViewModel by lazy {
        ViewModelProvider(this)[MovieTrailerViewModel::class.java]
    }

    override fun setupView(binding: FragmentMovieBinding) {
        val pagerAdapter = MoviePagerAdapter(this)
        val viewPager = binding.vpMovieContent
        viewPager.adapter = pagerAdapter

        val activity = (activity as AppCompatActivity)

        val toolbar = binding.toolbar.toolbar

        activity.setSupportActionBar(toolbar)

        activity.supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }

        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(pagerAdapter.TAB_TITLES[position])
        }.attach()

        val movieFragmentArgs by navArgs<MovieFragmentArgs>()

        movieViewModel.setMovieId(movieFragmentArgs.movieId)

        movieDetailViewModel.movie.observe(viewLifecycleOwner) {
            setContent(it)
            movieTrailerViewModel.setKey(it.trailerKey)
        }

    }

    private fun setContent(movie: Movie) {
        binding.apply {
            Glide.with(ivMovieCover.context)
                .load(movie.originalImage)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_baseline_broken_image_gray_24)
                )
                .into(ivMovieCover)

            tvMovieScore.text = movie.roundVoteAverage
            tvMovieDate.text = movie.fullReleaseDate
            toolbar.tvToolbarName.text = movie.title
        }
    }


}