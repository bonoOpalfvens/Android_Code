package com.example.fluxcode.network.persistence.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.fluxcode.network.CodeApi
import com.example.fluxcode.network.dtos.LoginDTO
import com.example.fluxcode.network.dtos.RegisterDTO
import com.example.fluxcode.network.persistence.LocalDB
import com.example.fluxcode.network.persistence.dbos.TokenDBO
import com.example.fluxcode.utils.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TokenRepository(private val database: LocalDB) {
    val token: String = database.tokenDAO.getToken().value?.token ?: ""

    suspend fun login(email: String, password: String){
        withContext(Dispatchers.IO){
            val response = CodeApi.retrofitService.login(LoginDTO(email, password))

            if(response.isSuccessful) {
                val token = response.body()!!.token
                UserService.setToken(token)
                insertToken(token)
            }else{
                if(response.code() == 400) throw Exception("Invalid credentials provided.")
                throw Exception("${response.code()}: ${response.message()}")
            }
        }
    }

    suspend fun register(email: String, username: String, password: String){
        withContext(Dispatchers.IO){
            val response = CodeApi.retrofitService.register(RegisterDTO(email, username, password))

            if(response.isSuccessful) {
                val token = response.body()!!.token
                UserService.setToken(token)
                insertToken(token)
            }else{
                if(response.code() == 400) throw Exception("Invalid credentials provided.")
                throw Exception("${response.code()}: ${response.message()}")
            }
        }
    }

    fun logout(){
        database.tokenDAO.deleteToken()
        UserService.setToken("")
    }

    private fun insertToken(token: String) {
        val tokenDBO = TokenDBO(token)
        database.tokenDAO.insertToken(tokenDBO)
        UserService.setToken(token)
    }
}