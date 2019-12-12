package com.example.fluxcode.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fluxcode.databinding.PostCommentListContentBinding
import com.example.fluxcode.domain.Comment
import com.squareup.picasso.Picasso

class PostCommentListAdapter(): ListAdapter<Comment, PostCommentListAdapter.ViewHolder>(PostCommentListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: PostCommentListContentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Comment) {
            binding.comment = item
            Picasso.get().load(item.user.avatar).into(binding.userImage)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostCommentListContentBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PostCommentListDiffCallback: DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.id == newItem.id && oldItem.likes == newItem.likes
    }
}
