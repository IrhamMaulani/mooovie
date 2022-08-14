package com.shiinasoftware.mooovie.ui.shared.genre

import androidx.recyclerview.widget.DiffUtil
import com.shiinasoftware.mooovie.models.Genre

class GenreDiffCallBack : DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean = oldItem == newItem
}