package com.shiinasoftware.mooovie.ui.shared.movie

import androidx.recyclerview.widget.DiffUtil
import com.shiinasoftware.mooovie.models.Genre
import com.shiinasoftware.mooovie.models.Movie

class MovieDiffCallBack : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
}