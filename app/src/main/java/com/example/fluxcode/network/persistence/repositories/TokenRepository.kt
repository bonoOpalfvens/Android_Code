package com.example.fluxcode.network.persistence.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.fluxcode.network.CodeApi
import com.example.fluxcode.network.dtos.LoginDTO
import com.example.fluxcode.network.dtos.RegisterDTO
import com.example.fluxcode.network.persistence.LocalDB
import com.example.fluxcode.network.persistence.dbos.TokenDBO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TokenRepository(private val database: LocalDB) {
    val token: LiveData<String> = Transformations.map(database.tokenDAO.getToken()){it?.token}

    suspend fun login(email: String, password: String){
        withContext(Dispatchers.Main){
            val response = CodeApi.retrofitService.login(LoginDTO(email, password))

            if(response.isSuccessful) {
                val token = response.body()!!.token
                // Save token in DB
                withContext(Dispatchers.IO){
                    database.tokenDAO.insertToken(TokenDBO(token))
                }
            }else{
                if(response.code() == 400) throw Exception("Invalid credentials provided.")
                throw Exception("${response.code()}: ${response.message()}")
            }
        }
    }

    suspend fun register(email: String, username: String, password: String){
        withContext(Dispatchers.Main){
            val response = CodeApi.retrofitService.register(RegisterDTO(email, username, password))

            if(response.isSuccessful) {
                val token = response.body()!!.token
                // Save token in DB
                withContext(Dispatchers.IO){
                    database.tokenDAO.insertToken(TokenDBO(token))
                }
            }else{
                if(response.code() == 400) throw Exception("Invalid credentials provided.")
                throw Exception("${response.code()}: ${response.message()}")
            }
        }
    }

    suspend fun logout(){
        withContext(Dispatchers.IO){
            database.tokenDAO.deleteToken()
        }
    }
}