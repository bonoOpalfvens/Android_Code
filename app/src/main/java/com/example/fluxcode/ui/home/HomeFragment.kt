package com.example.fluxcode.ui.home

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fluxcode.R
import com.example.fluxcode.databinding.FragmentHomeBinding
import com.example.fluxcode.utils.UserService

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val application = requireNotNull(this.activity).application

        val vmf = HomeViewModelFactory(application)
        homeViewModel = ViewModelProviders.of(this, vmf).get(HomeViewModel::class.java)
        binding.viewModel = homeViewModel

        binding.registerButton.setOnClickListener {
            this.findNavController().navigate(R.id.nav_register)
        }
        binding.loginButton.setOnClickListener {
            this.findNavController().navigate(R.id.nav_login)
        }

        // recyclerview
        val manager = GridLayoutManager(activity, 1)
        binding.homePostList.layoutManager = manager
        val adapter = HomePostListAdapter(HomePostListListener(
            _viewPost = {
                homeViewModel.viewPost(it)
            },
            _likePost = {
                homeViewModel.likePost(it)
            }
        ))
        binding.homePostList.adapter = adapter
        homeViewModel.posts.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.sortedWith(compareBy({ p -> -p.likes }, {p -> -p.noComments})))
        })

        // safeArgs navigation
        homeViewModel.post.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                this.findNavController().navigate(HomeFragmentDirections.actionNavHomeToPostFragment(it))
                homeViewModel.onNavigated()
            }
        })

        // Responsive layout for logged in users
        UserService.token.observe(this, Observer {
            if(UserService.loggedIn){
                binding.homeLoginLayout.isVisible = false
                binding.textIntro.isVisible = false
            }else {
                binding.homeLoginLayout.isVisible = true
                binding.textIntro.isVisible = true
            }
        })

        binding.lifecycleOwner = this
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    internal class HomeViewModelFactory(val app: Application) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) return HomeViewModel(app) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}