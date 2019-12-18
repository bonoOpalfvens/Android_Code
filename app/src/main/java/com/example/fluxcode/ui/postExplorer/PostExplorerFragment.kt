package com.example.fluxcode.ui.postExplorer

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fluxcode.R
import com.example.fluxcode.databinding.FragmentPostExplorerBinding
import com.example.fluxcode.domain.Board
import com.example.fluxcode.ui.home.HomePostListAdapter
import com.example.fluxcode.ui.home.HomePostListListener

class PostExplorerFragment : Fragment() {
    private lateinit var postExplorerViewModel: PostExplorerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentPostExplorerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_explorer, container, false)
        val application = requireNotNull(this.activity).application
        val board = PostExplorerFragmentArgs.fromBundle(arguments!!).board

        val vmf = PostExplorerViewModelFactory(application, board)
        postExplorerViewModel = ViewModelProviders.of(this, vmf).get(PostExplorerViewModel::class.java)
        binding.viewModel = postExplorerViewModel

        // recyclerview
        val manager = GridLayoutManager(activity, 1)
        binding.postExplorerList.layoutManager = manager
        val adapter = HomePostListAdapter(HomePostListListener(
            _viewPost = {
                postExplorerViewModel.viewPost(it)
            },
            _likePost = {
                postExplorerViewModel.likePost(it)
            }
        ))
        binding.postExplorerList.adapter = adapter
        postExplorerViewModel.posts.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.sortedWith(compareBy({ p -> -p.likes }, {p -> -p.noComments})))
        })

        // safeArgs navigation
        postExplorerViewModel.post.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                this.findNavController().navigate(PostExplorerFragmentDirections.actionPostExplorerFragmentToPostFragment(it))
                postExplorerViewModel.onNavigated()
            }
        })

        binding.lifecycleOwner = this
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    internal class PostExplorerViewModelFactory(val app: Application, val board: Board) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PostExplorerViewModel::class.java)) return PostExplorerViewModel(app, board) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}