package com.example.fluxcode.utils

import androidx.lifecycle.LiveData

class UserService {
    companion object {
        lateinit var token: LiveData<String>

        val loggedIn get() = !token.value.isNullOrBlank()
    }
}