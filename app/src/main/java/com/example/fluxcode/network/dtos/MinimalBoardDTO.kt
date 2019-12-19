package com.example.fluxcode.network.dtos

import com.example.fluxcode.domain.Board
import com.example.fluxcode.network.persistence.dbos.BoardDBO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MinimalBoardDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String,
    @Json(name = "noPosts") val noPosts: Int,
    @Json(name = "likes") val likes: Int,
    @Json(name = "isLiking") val isLiking: Boolean
) {
    fun toDomain() : Board {
        return Board(id, name, description, icon, likes, noPosts, isLiking)
    }
    fun toDBO() : BoardDBO {
        return BoardDBO(id, name, description, icon, likes, noPosts)
    }
}