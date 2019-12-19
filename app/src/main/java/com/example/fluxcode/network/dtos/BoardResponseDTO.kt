package com.example.fluxcode.network.dtos

import com.example.fluxcode.domain.Board
import com.example.fluxcode.network.persistence.dbos.BoardDBO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BoardResponseDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String,
    @Json(name = "posts") val posts: List<MinimalPostDTO>,
    @Json(name = "noPosts") val noPosts: Int,
    @Json(name = "likes") val likes: Int,
    @Json(name = "isLiking") val isLiking: Boolean
) {
    fun toDomain() : Board {
        return Board(id, name, description, icon, likes, noPosts, isLiking)
            .loadPosts(posts.map { it.toDomain().loadBoard(MinimalBoardDTO(id, name, description, icon, noPosts, likes, isLiking).toDomain()) })
    }
    fun toDBO() : BoardDBO {
        return BoardDBO(id, name, description, icon, likes, noPosts)
    }
}