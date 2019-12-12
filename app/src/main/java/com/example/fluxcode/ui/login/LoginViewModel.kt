package com.example.fluxcode.ui.login

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fluxcode.network.CodeApi
import com.example.fluxcode.network.dtos.LoginDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : ViewModel() {
    private val app = app

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

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
                    Toast.makeText(app, response.body()?.token, Toast.LENGTH_LONG).show()
                }else{
                    throw Exception("${response.code()}: ${response.message()}")
                }
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}