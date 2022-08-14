package com.shiinasoftware.mooovie.ui.shared.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shiinasoftware.mooovie.R
import com.shiinasoftware.mooovie.databinding.ReviewItemBinding
import com.shiinasoftware.mooovie.models.Review

class ReviewListAdapter : PagingDataAdapter<Review,
        ReviewListAdapter.ListViewHolder>(ReviewDiffCallBack()) {

    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ReviewItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ListViewHolder(private val itemBinding: ReviewItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(review: Review) {
            itemBinding.tvContent.text = review.content
            itemBinding.tvUsername.text = review.authorDetail?.username

            Glide.with(itemBinding.ivUser.context)
                .load(review.authorDetail?.avatarPathClean)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_baseline_broken_image_gray_24)
                )
                .into(itemBinding.ivUser)
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(review) }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(review: Review)
    }
}