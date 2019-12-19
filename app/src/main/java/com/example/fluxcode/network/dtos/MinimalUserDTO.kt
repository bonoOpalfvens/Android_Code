package com.example.fluxcode.network.dtos

import com.example.fluxcode.domain.User
import com.example.fluxcode.network.persistence.dbos.UserDBO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MinimalUserDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "userName") val userName: String,
    @Json(name = "displayName") val displayName: String,
    @Json(name = "avatar") val avatar: String,
    @Json(name = "email") val email: String,
    @Json(name = "emailIsPrivate") val emailIsPrivate: Boolean,
    @Json(name = "noLikedPosts") val noLikedPosts: Int,
    @Json(name = "noCreatedPosts") val noCreatedPosts: Int,
    @Json(name = "noCreatedComments") val noCreatedComments: Int
) {
    fun toDomain() : User {
        return User(id, userName, displayName, avatar, email, emailIsPrivate, "", "", "", "", noLikedPosts, noCreatedPosts, noCreatedComments)
    }
    fun toDBO() : UserDBO {
        return UserDBO(id, userName, displayName, avatar, email, emailIsPrivate, "", "", "", "", noLikedPosts, noCreatedPosts, noCreatedComments)
    }
}