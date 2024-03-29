package com.example.fluxcode.ui.register

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fluxcode.network.persistence.getDatabase
import com.example.fluxcode.network.persistence.repositories.TokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class RegisterViewModel(app: Application) : ViewModel(){
    private val app = app

    // database connection
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = getDatabase(app)
    private val tokenRepository = TokenRepository(database)

    // fields
    val email = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    fun register(){
        try{
            if(email.value.isNullOrBlank() || !email.value!!.matches(Regex("^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])\$")))
            { throw IllegalArgumentException("Invalid argument provided for email") }
            if(username.value.isNullOrBlank() || !username.value!!.matches(Regex("^[A-Za-z0-9_-]+\$")))
            { throw IllegalArgumentException("Invalid argument provided for username\nUsernames can only contain the following characters: upper case (A-Z), lower case (a-z), number (0-9), underscores and hyphens") }
            if(password.value.isNullOrBlank() || !password.value!!.matches(Regex("^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){6,30}\$")))
            { throw IllegalArgumentException("Invalid argument provided for password\nPassword must contain at least one of each of the following: upper case (A-Z), lower case (a-z), number (0-9) and special character (! ? | % \$ # _ - @)") }
            if(!password.value.equals(confirmPassword.value))
            { throw IllegalArgumentException("Passwords don't match") }

            registerRequest()
        }catch (e: Exception){
            Toast.makeText(app, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun registerRequest() {
        viewModelScope.launch {
            try{
                tokenRepository.register(email.value!!, username.value!!, password.value!!)
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // clear all jobs on clear
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}