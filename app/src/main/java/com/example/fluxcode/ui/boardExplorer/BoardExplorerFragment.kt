package com.example.fluxcode.ui.boardExplorer

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fluxcode.R
import com.example.fluxcode.databinding.FragmentBoardExplorerBinding

class BoardExplorerFragment : Fragment() {
    private lateinit var boardExplorerViewModel: BoardExplorerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentBoardExplorerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_board_explorer, container, false)
        val application = requireNotNull(this.activity).application

        val vmf = BoardExplorerViewModelFactory(application)
        boardExplorerViewModel = ViewModelProviders.of(this, vmf).get(BoardExplorerViewModel::class.java)
        binding.viewModel = boardExplorerViewModel

        // recyclerview
        val manager = GridLayoutManager(activity, 3)
        binding.boardExplorerList.layoutManager = manager
        val adapter = BoardExplorerListAdapter(BoardExplorerListListener(
            _viewBoard = {
                boardExplorerViewModel.viewBoard(it)
            }
        ))
        binding.boardExplorerList.adapter = adapter
        boardExplorerViewModel.boards.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.sortedWith(compareBy {p -> p.id}))
        })

        // safeArgs navigation
        boardExplorerViewModel.board.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                this.findNavController().navigate(BoardExplorerFragmentDirections.actionNavBoardsToPostExplorerFragment(it))
                boardExplorerViewModel.onNavigated()
            }
        })

        binding.lifecycleOwner = this
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    internal class BoardExplorerViewModelFactory(val app: Application) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BoardExplorerViewModel::class.java)) return BoardExplorerViewModel(app) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}