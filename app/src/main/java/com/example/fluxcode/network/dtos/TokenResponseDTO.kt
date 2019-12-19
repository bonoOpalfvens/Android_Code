package com.example.fluxcode.network.dtos

import com.example.fluxcode.network.persistence.dbos.TokenDBO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenResponseDTO(
    @Json(name = "token") val token: String
){
    fun toDBO(): TokenDBO {
        return TokenDBO(token)
    }
}