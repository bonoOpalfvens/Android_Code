package com.example.fluxcode.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UserService {
    companion object {
        private val _token = MutableLiveData<String>()
        val token: LiveData<String> get() = _token

        val loggedIn get() = !token.value.isNullOrBlank()

        fun setToken(token: String) {
            _token.value = token
        }
    }
}