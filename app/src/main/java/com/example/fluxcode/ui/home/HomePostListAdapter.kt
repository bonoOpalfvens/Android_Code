package com.example.fluxcode.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fluxcode.databinding.HomePostListContentBinding
import com.example.fluxcode.domain.Post
import com.squareup.picasso.Picasso

class HomePostListAdapter(val clickListener: HomePostListListener): ListAdapter<Post, HomePostListAdapter.ViewHolder>(HomePostListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: HomePostListContentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post, clickListener: HomePostListListener) {
            binding.post = item
            binding.clickListener = clickListener
            Picasso.get().load(item.board.icon).into(binding.boardImage)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HomePostListContentBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class HomePostListDiffCallback: DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id && oldItem.likes == newItem.likes && oldItem.noComments == newItem.likes
    }
}

class HomePostListListener(val _onClick: (post: Post)->Unit) {
    fun onClick(post: Post) = _onClick(post)
}
