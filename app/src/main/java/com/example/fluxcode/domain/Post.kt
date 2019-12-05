package com.example.fluxcode.domain

import java.io.Serializable
import java.util.*

class Post : Serializable {
    constructor(id: Int, title: String, content: String, user: User, dateAdded: Date, noComments: Int, likes: Int, isLiking: Boolean){
        this.id = id
        this.title = title
        this.content = content
        this.user = user
        this.dateAdded = dateAdded
        this._noComments = noComments
        this._likes = likes
        this._isLiking = isLiking
    }

    val id: Int
    val title: String
    val content: String
    val user: User
    val dateAdded: Date

    private var _noComments: Int
    val noComments: Int get() = _noComments

    private var _likes: Int
    val likes: Int get() = _likes

    private var _isLiking: Boolean
    val isLiking: Boolean get() = _isLiking

    private var _comments = mutableListOf<Comment>()
    val comments: List<Comment> get() = _comments

    private lateinit var _board: Board
    val board: Board get() = _board

    fun loadBoard(board: Board){
        _board = board
    }

    fun loadComments(comments: List<Comment>){
        _comments = comments as MutableList<Comment>
    }

    fun like(){
        if(isLiking) {
            _likes--
        }else{
            _likes++
        }
        _isLiking = !isLiking
    }
}