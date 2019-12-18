package com.example.fluxcode.ui.post

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fluxcode.R
import com.example.fluxcode.databinding.FragmentPostBinding
import com.example.fluxcode.domain.Post
import com.example.fluxcode.utils.UserService
import com.squareup.picasso.Picasso

class PostFragment : Fragment() {
    private lateinit var postViewModel: PostViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentPostBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)
        val application = requireNotNull(this.activity).application
        val post = PostFragmentArgs.fromBundle(arguments!!).post

        val vmf = PostViewModelFactory(application, post)
        postViewModel = ViewModelProviders.of(this, vmf).get(PostViewModel::class.java)
        binding.viewModel = postViewModel

        Picasso.get().load(post.board.icon).into(binding.boardImage)

        // recyclerview
        val manager = GridLayoutManager(activity, 1)
        binding.commentList.layoutManager = manager
        val adapter = PostCommentListAdapter()
        binding.commentList.adapter = adapter
        postViewModel.post.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.comments.sortedByDescending { c -> c.id })
        })

        binding.lifecycleOwner = this
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    internal class PostViewModelFactory(val app: Application, val post: Post) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PostViewModel::class.java)) return PostViewModel(app, post) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}