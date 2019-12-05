package com.example.fluxcode.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.fluxcode.R

class RegisterFragment : Fragment() {
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_register, container, false)

        return root
    }
}