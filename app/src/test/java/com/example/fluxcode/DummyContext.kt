package com.example.fluxcode

import com.example.fluxcode.domain.Board
import com.example.fluxcode.domain.Comment
import com.example.fluxcode.domain.Post
import com.example.fluxcode.domain.User

class DummyContext {
    val boards = listOf(
        Board(1, "C#", "Programming language", "https://i.imgur.com/HUBBvDr.png", 1, 0, true),
        Board(2, "Kotlin", "Programming language", "https://i.imgur.com/lgNXztq.png", 0, 2, false)
    )
    val user = User(1, "testUser", "testUser", "https://i.imgur.com/bMxp0CX.png", "test2@fluxcode.be", false, "", "", "An avid programming looking to contribute", "", 2, 2, 1)
    val post = listOf(
        Post(1, "Retrofit with kotlin coroutines", "Retrofitting refers to the addition of new technology or features to older systems, for example: power plant retrofit, improving power plant efficiency", user, "15/12/2019", 1, 1, true),
        Post(2, "Persistence with room", "After years of being held captive in an isolated shed by a kidnapper, Joy and her little son, Jack, manage to escape the confinement and gain their freedom.", user, "16/12/2019", 0, 1, true)
    )
    val comment = Comment(1, user, "16/12/2019", "Great summary!", 0, false)

    init {
        post[0].loadBoard(boards[1]).loadComments(listOf(comment))
        post[1].loadBoard(boards[1])
        boards[1].loadPosts(post)
    }
}