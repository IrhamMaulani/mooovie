package com.shiinasoftware.mooovie.ui.shared.review

import androidx.recyclerview.widget.DiffUtil
import com.shiinasoftware.mooovie.models.Review

class ReviewDiffCallBack: DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean = oldItem == newItem
}