package com.example.fluxcode.network.persistence.dbos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fluxcode.domain.Comment
import com.example.fluxcode.domain.User

@Entity
data class CommentDBO constructor(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val dateAdded: String,
    val content: String,
    val likes: Int,
    val postId: Int
){
    fun toDomain(user: User) : Comment {
        return Comment(id, user, dateAdded, content, likes, false)
    }
}
