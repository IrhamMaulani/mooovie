package com.shiinasoftware.mooovie.ui.shared.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shiinasoftware.mooovie.R
import com.shiinasoftware.mooovie.databinding.MovieItemBinding
import com.shiinasoftware.mooovie.models.Movie

class MovieListAdapter : PagingDataAdapter<Movie,
        MovieListAdapter.ListViewHolder>(MovieDiffCallBack()) {

    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ListViewHolder(private val itemBinding: MovieItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movie: Movie) {
            itemBinding.tvMovieTitle.text = movie.title
            itemBinding.tvRating.text = movie.voteAverage.toString()
            Glide.with(itemBinding.ivMoviePoster.context)
                .load(movie.smallImage)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_baseline_broken_image_gray_24)
                )
                .into(itemBinding.ivMoviePoster)
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(movie) }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(movie: Movie)
    }
}