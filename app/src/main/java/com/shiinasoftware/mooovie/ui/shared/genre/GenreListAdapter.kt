package com.shiinasoftware.mooovie.ui.shared.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shiinasoftware.mooovie.databinding.GenreItemBinding
import com.shiinasoftware.mooovie.models.Genre

class GenreListAdapter : ListAdapter<Genre,
        GenreListAdapter.ListViewHolder>(GenreDiffCallBack()) {

    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = GenreItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ListViewHolder(private val itemBinding: GenreItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(genre: Genre) {
            itemBinding.tvCategoryName.text = genre.name
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(genre) }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(genre: Genre)
    }
}