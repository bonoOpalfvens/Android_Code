package com.example.fluxcode.ui.register

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.fluxcode.R
import com.example.fluxcode.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        val application = requireNotNull(this.activity).application

        val vmf = RegisterViewModelFactory(application)
        registerViewModel = ViewModelProviders.of(this, vmf).get(RegisterViewModel::class.java)
        binding.viewModel = registerViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    internal class RegisterViewModelFactory(val app: Application) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) return RegisterViewModel(app) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}