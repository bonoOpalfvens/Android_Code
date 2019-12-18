package com.example.fluxcode.ui.login

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fluxcode.network.CodeApi
import com.example.fluxcode.network.dtos.LoginDTO
import com.example.fluxcode.utils.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : ViewModel() {
    private val app = app

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    init {
        UserService.setToken("")
    }

    fun login(){
        try{
            if(email.value.isNullOrBlank() || !email.value!!.matches(Regex("^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])\$")))
            { throw IllegalArgumentException("Invalid argument provided for email") }
            if(password.value.isNullOrBlank() || !password.value!!.matches(Regex("^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){6,30}\$")))
            { throw IllegalArgumentException("Invalid argument provided for password\nPassword must contain at least one of each of the following: upper case (A-Z), lower case (a-z), number (0-9) and special character (! ? | % \$ # _ - @)") }

            loginRequest()
        }catch (e: Exception){
            Toast.makeText(app, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun loginRequest(){
        GlobalScope.launch(Dispatchers.Main) {
            try{
                val response = CodeApi.retrofitService.login(LoginDTO(email.value!!, password.value!!))

                if(response.isSuccessful) {
                    UserService.setToken(response.body()!!.token)
                }else{
                    if(response.code() == 400) throw Exception("Invalid credentials provided.")
                    throw Exception("${response.code()}: ${response.message()}")
                }
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}