package com.example.fluxcode.network.dtos

import com.example.fluxcode.domain.Post
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostDetailResponseDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "board") val board: MinimalBoardDTO,
    @Json(name = "user") val user: MinimalUserDTO,
    @Json(name = "dateAdded") val dateAdded: String,
    @Json(name = "comments") val comments: List<CommentDTO>,
    @Json(name = "noComments") val noComments: Int,
    @Json(name = "likes") val likes: Int,
    @Json(name = "isLiking") val isLiking: Boolean
) {
    fun toDomain() : Post {
        return Post(id, title, content, user.toDomain(), dateAdded, noComments, likes, isLiking)
            .loadBoard(board.toDomain()).loadComments(comments.map { it.toDomain() })
    }
}