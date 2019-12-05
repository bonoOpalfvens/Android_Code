package com.example.fluxcode.domain

import java.io.Serializable

class User : Serializable {
    constructor(
        id: Int,
        username: String,
        displayName: String,
        avatar: String,
        email: String,
        emailIsPrivate: Boolean,
        firstName: String,
        lastName: String,
        description: String,
        github: String,
        noLikedPosts: Int,
        noCreatedPosts: Int,
        noCreatedComments: Int
    ){
        this.id = id
        this.username = username
        this.displayName = displayName
        this.avatar = avatar
        this.email = email
        this.emailIsPrivate = emailIsPrivate
        this.firstName = firstName
        this.lastName = lastName
        this.description = description
        this.github = github
        this.noLikedPosts = noLikedPosts
        this.noCreatedPosts = noCreatedPosts
        this.noCreatedComments = noCreatedComments
    }

    val id: Int
    val username: String
    val displayName: String
    val avatar: String
    val email: String
    val emailIsPrivate: Boolean
    val firstName: String
    val lastName: String
    val description: String
    val github: String
    val noLikedPosts: Int
    val noCreatedPosts: Int
    val noCreatedComments: Int

    private var _boards = mutableListOf<Board>()
    val boards: List<Board> get() = _boards

    fun loadBoards(boards: List<Board>){
        _boards = boards as MutableList<Board>
    }

    fun followBoard(board: Board){
        if(!boards.any { it.id == board.id }) _boards.add(board)
    }
}