package com.example.fluxcode.network.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fluxcode.network.persistence.dbos.TokenDBO

@Dao
interface TokenDAO {
    @Query("SELECT * FROM TokenDBO LIMIT 1")
    fun getToken() : LiveData<TokenDBO>

    @Insert
    fun insertToken(token : TokenDBO)

    @Query("DELETE FROM TokenDBO")
    fun deleteToken()
}