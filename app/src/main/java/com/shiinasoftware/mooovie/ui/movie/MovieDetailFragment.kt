package com.shiinasoftware.mooovie.ui.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shiinasoftware.mooovie.BaseFragmentBinding
import com.shiinasoftware.mooovie.databinding.FragmentMovieDetailBinding
import com.shiinasoftware.mooovie.models.Genre
import com.shiinasoftware.mooovie.models.Movie
import com.shiinasoftware.mooovie.ui.home.HomeFragmentDirections
import com.shiinasoftware.mooovie.ui.shared.genre.GenreListAdapter

class MovieDetailFragment : BaseFragmentBinding<FragmentMovieDetailBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieDetailBinding =
        FragmentMovieDetailBinding::inflate


    private val movieViewModel: MovieViewModel by lazy {
        ViewModelProvider(requireParentFragment())[MovieViewModel::class.java]
    }

    private val movieDetailViewModel: MovieDetailViewModel by lazy {
        ViewModelProvider(requireParentFragment())[MovieDetailViewModel::class.java]
    }

    private lateinit var viewModel: MovieDetailViewModel

    override fun setupView(binding: FragmentMovieDetailBinding) {

        val genreListAdapter = GenreListAdapter()
        showGenreRecycler(genreListAdapter)

        movieViewModel.movieId.observe(viewLifecycleOwner) {
            movieDetailViewModel.showMovie(it)
        }

        movieDetailViewModel.movie.observe(viewLifecycleOwner) {
            setContent(it)
            movieDetailViewModel.setGenres(it.genres ?: listOf(Genre()))
        }

        movieDetailViewModel.genres.observe(viewLifecycleOwner) {
            genreListAdapter.submitList(it)
        }
    }


    private fun setContent(movie: Movie) {
        binding.apply {
            tvMovieTitle.text = movie.title
            tvOverview.text = movie.overview
        }
    }

    private fun showGenreRecycler(genreListAdapter: GenreListAdapter) {
        val drainageRecyclerView = binding.rvRecyclerGenres
        drainageRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        drainageRecyclerView.adapter = genreListAdapter

        genreListAdapter.submitList(ArrayList<Genre>())

        genreListAdapter.setOnItemClickCallback(object :
            GenreListAdapter.OnItemClickCallback {
            override fun onItemClicked(genre: Genre) {
                NavHostFragment.findNavController(this@MovieDetailFragment)
                    .navigate(
                        MovieFragmentDirections.actionMovieFragmentToGenreMovieFragment(
                            genre
                        )
                    )
            }
        })
    }


}