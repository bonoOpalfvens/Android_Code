package com.example.fluxcode.ui.boardExplorer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fluxcode.databinding.BoardExplorerListContentBinding
import com.example.fluxcode.domain.Board
import com.squareup.picasso.Picasso

class BoardExplorerListAdapter(val clickListener: BoardExplorerListListener): ListAdapter<Board, BoardExplorerListAdapter.ViewHolder>(BoardExplorerListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: BoardExplorerListContentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Board, clickListener: BoardExplorerListListener) {
            binding.board = item
            binding.clickListener = clickListener
            Picasso.get().load(item.icon).into(binding.boardImage)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BoardExplorerListContentBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class BoardExplorerListDiffCallback: DiffUtil.ItemCallback<Board>() {
    override fun areItemsTheSame(oldItem: Board, newItem: Board): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Board, newItem: Board): Boolean {
        return oldItem.id == newItem.id
    }
}

class BoardExplorerListListener(val _viewBoard: (board: Board)->Unit) {
    fun viewBoard(board: Board) = _viewBoard(board)
}
