package com.shiinasoftware.mooovie.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.shiinasoftware.mooovie.BaseFragmentBinding
import com.shiinasoftware.mooovie.R
import com.shiinasoftware.mooovie.databinding.FragmentHomeBinding
import com.shiinasoftware.mooovie.models.Genre
import com.shiinasoftware.mooovie.models.Movie
import com.shiinasoftware.mooovie.ui.shared.genre.GenreListAdapter
import com.shiinasoftware.mooovie.ui.shared.genre.GenreViewModel
import com.shiinasoftware.mooovie.ui.shared.movie.MovieListAdapter
import com.shiinasoftware.mooovie.ui.shared.movie.MovieViewModel
import com.shiinasoftware.mooovie.ui.shared.utils.loadDataState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseFragmentBinding<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    private lateinit var viewModel: HomeViewModel

    private lateinit var genreSkeleton: Skeleton
    private lateinit var movieSkeleton: Skeleton

    private val genreViewModel: GenreViewModel by lazy {
        ViewModelProvider(this)[GenreViewModel::class.java]
    }

    private val movieViewModel: MovieViewModel by lazy {
        ViewModelProvider(this)[MovieViewModel::class.java]
    }

    override fun setupView(binding: FragmentHomeBinding) {
        val appBarLayout = binding.includeToolbar.appBar

        movieViewModel.getMovies()

        val genreListAdapter = GenreListAdapter()
        showGenreRecycler(genreListAdapter)

        val movieListAdapter = MovieListAdapter()
        showMovieRecycler(movieListAdapter)

        genreSkeleton = binding.rvRecyclerGenres.applySkeleton(R.layout.genre_item)
        movieSkeleton = binding.rvMovie.applySkeleton(R.layout.movie_item)
        movieSkeleton.showSkeleton()

        appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            if (verticalOffset != 0) {
                //  Collapsed
                binding.includeToolbar.tvToolbarName.text = context?.getText(R.string.app_name)
                // disabled swipeRefresh when toolbar collapsed to fix weird bug when
                // screen scrolled up swipeRefresh executed
            } else {
                //Expanded
                binding.includeToolbar.tvToolbarName.text = ""
            }
        }

        lifecycle.coroutineScope.launch {
            movieSkeleton.showOriginal()
            movieViewModel.movies.collectLatest {
                movieListAdapter.submitData(lifecycle, it)
            }
        }

        genreViewModel.genres.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) genreListAdapter.submitList(it)
        }

        genreViewModel.status.observe(viewLifecycleOwner) {
            loadDataState(genreSkeleton, it, binding.root)
        }

    }

    private fun showGenreRecycler(genreListAdapter: GenreListAdapter) {
        val genreListRecyclerView = binding.rvRecyclerGenres
        genreListRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        genreListRecyclerView.adapter = genreListAdapter

        genreListAdapter.submitList(ArrayList<Genre>())

        genreListAdapter.setOnItemClickCallback(object :
            GenreListAdapter.OnItemClickCallback {
            override fun onItemClicked(genre: Genre) {
                NavHostFragment.findNavController(this@HomeFragment)
                    .navigate(HomeFragmentDirections.actionHomeFragmentToGenreMovieFragment(genre))
            }
        })
    }

    private fun showMovieRecycler(movieListAdapter: MovieListAdapter) {
        val movieListRecyclerView = binding.rvMovie
        movieListRecyclerView.layoutManager = GridLayoutManager(context, 2)
        movieListRecyclerView.adapter = movieListAdapter

        movieListAdapter.setOnItemClickCallback(object :
            MovieListAdapter.OnItemClickCallback {
            override fun onItemClicked(movie: Movie) {
                NavHostFragment.findNavController(this@HomeFragment)
                    .navigate(HomeFragmentDirections.actionHomeFragmentToMovieFragment(movie.id))
            }
        })
    }

}