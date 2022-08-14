package com.shiinasoftware.mooovie.ui.movie


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.shiinasoftware.mooovie.BaseFragmentBinding
import com.shiinasoftware.mooovie.databinding.FragmentMovieTrailerBinding


class MovieTrailerFragment : BaseFragmentBinding<FragmentMovieTrailerBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieTrailerBinding =
        FragmentMovieTrailerBinding::inflate

    private val movieTrailerViewModel: MovieTrailerViewModel by lazy {
        ViewModelProvider(requireParentFragment())[MovieTrailerViewModel::class.java]
    }

    override fun setupView(binding: FragmentMovieTrailerBinding) {
        val youTubePlayerView: YouTubePlayerView = binding.youtubePlayerView

        lifecycle.addObserver(youTubePlayerView)

        movieTrailerViewModel.key.observe(viewLifecycleOwner) {
            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = it
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            })
        }


    }
}