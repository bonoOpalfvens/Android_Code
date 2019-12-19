package com.example.fluxcode.network.persistence.dbos

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fluxcode.domain.Post
import com.example.fluxcode.domain.User

@Entity
data class PostDBO constructor(
    @PrimaryKey
    val postId: Int,
    val title: String,
    val content: String,
    val userIdF: Int,
    val dateAdded: String,
    val noComments: Int,
    val postLikes: Int,
    val boardIdF: Int
){
    fun toDomain(user: User) : Post {
        return Post(postId, title, content, user, dateAdded, noComments, postLikes, false)
    }
}

class PostWithUserAndBoard {
    @Embedded
    lateinit var post: PostDBO
    @Embedded
    lateinit var user: UserDBO
    @Embedded
    lateinit var board: BoardDBO

    fun toDomain() : Post {
        return Post(post.postId, post.title, post.content, user.toDomain(), post.dateAdded, post.noComments, post.postLikes, false)
            .loadBoard(board.toDomain())
    }
}
