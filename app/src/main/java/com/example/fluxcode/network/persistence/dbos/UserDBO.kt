package com.example.fluxcode.network.persistence.dbos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fluxcode.domain.User

@Entity
data class UserDBO constructor(
    @PrimaryKey
    val userId: Int,
    val userName: String,
    val displayName: String,
    val avatar: String,
    val email: String,
    val emailIsPrivate: Boolean,
    val firstName: String,
    val lastName: String,
    val userDescription: String,
    val github: String,
    val noLikedPosts: Int,
    val noCreatedPosts: Int,
    val noCreatedComments: Int
){
    fun toDomain() : User {
        return User(userId, userName, displayName, avatar, email, emailIsPrivate, firstName, lastName, userDescription, github, noLikedPosts, noCreatedPosts, noCreatedComments)
    }
}