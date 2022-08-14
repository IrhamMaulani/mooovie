package com.shiinasoftware.mooovie.ui.genremovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.shiinasoftware.mooovie.BaseFragmentBinding
import com.shiinasoftware.mooovie.R
import com.shiinasoftware.mooovie.databinding.FragmentGenreMovieBinding
import com.shiinasoftware.mooovie.models.Movie
import com.shiinasoftware.mooovie.ui.shared.movie.MovieListAdapter
import com.shiinasoftware.mooovie.ui.shared.movie.MovieViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GenreMovieFragment : BaseFragmentBinding<FragmentGenreMovieBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGenreMovieBinding =
        FragmentGenreMovieBinding::inflate

    private val movieViewModel: MovieViewModel by lazy {
        ViewModelProvider(this)[MovieViewModel::class.java]
    }

    private val genreMovieViewModel: GenreMovieViewModel by lazy {
        ViewModelProvider(this)[GenreMovieViewModel::class.java]
    }

    private lateinit var movieSkeleton: Skeleton

    override fun setupView(binding: FragmentGenreMovieBinding) {

        val genreMovieFragmentArgs by navArgs<GenreMovieFragmentArgs>()

        binding.tvGenreLabel.text = genreMovieFragmentArgs.genre.name

        val movieListAdapter = MovieListAdapter()
        showMovieRecycler(movieListAdapter)

        movieViewModel.getMovies(genreMovieFragmentArgs.genre.id)

        movieSkeleton = binding.rvMovie.applySkeleton(R.layout.movie_item)

        lifecycle.coroutineScope.launch {
            movieViewModel.movies.collectLatest {
                movieListAdapter.submitData(lifecycle, it)
            }
            movieSkeleton.showOriginal()
        }

        binding.btnBack.setOnClickListener {
            genreMovieViewModel.backToHome()
        }

        genreMovieViewModel.backToHome.observe(viewLifecycleOwner) {
            if (it) findNavController().navigateUp()
        }

    }

    private fun showMovieRecycler(movieListAdapter: MovieListAdapter) {
        val movieListRecyclerView = binding.rvMovie
        movieListRecyclerView.layoutManager = GridLayoutManager(context, 2)
        movieListRecyclerView.adapter = movieListAdapter

        movieListAdapter.setOnItemClickCallback(object :
            MovieListAdapter.OnItemClickCallback {
            override fun onItemClicked(movie: Movie) {
                NavHostFragment.findNavController(this@GenreMovieFragment)
                    .navigate(
                        GenreMovieFragmentDirections.actionGenreMovieFragmentToMovieFragment(
                            movie.id
                        )
                    )
            }
        })
    }

}
