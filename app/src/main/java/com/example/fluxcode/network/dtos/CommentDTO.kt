package com.example.fluxcode.network.dtos

import com.example.fluxcode.domain.Comment
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "user") val user: MinimalUserDTO,
    @Json(name = "dateAdded") val dateAdded: String,
    @Json(name = "content") val content: String,
    @Json(name = "likes") val likes: Int,
    @Json(name = "isLiking") val isLiking: Boolean
) {
    fun toDomain() : Comment {
        return Comment(id, user.toDomain(), dateAdded, content, likes, isLiking)
    }
}