package com.example.fluxcode.ui.register

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fluxcode.network.CodeApi
import com.example.fluxcode.network.dtos.RegisterDTO
import com.example.fluxcode.utils.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterViewModel(app: Application) : ViewModel(){
    private val app = app

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
        GlobalScope.launch(Dispatchers.Main) {
            try{
                val response = CodeApi.retrofitService.register(RegisterDTO(email.value!!, username.value!!, password.value!!))

                if(response.isSuccessful) {
                    Toast.makeText(app, response.body()?.token, Toast.LENGTH_LONG).show()
                    UserService.setToken(response.body()!!.token)
                }else{
                    if(response.code() == 400) throw Exception("Email or username")
                    throw Exception("${response.code()}: ${response.message()}")
                }
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}