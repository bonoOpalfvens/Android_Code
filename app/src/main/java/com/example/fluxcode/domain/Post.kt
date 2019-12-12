package com.example.fluxcode.domain

import java.io.Serializable

class Post : Serializable {
    constructor(id: Int, title: String, content: String, user: User, dateAdded: String, noComments: Int, likes: Int, isLiking: Boolean){
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
    private val dateAdded: String

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

    fun shortContent(): String {
        if(content.length > 250)
            return content.substring(0, 250).plus("...")
        return content
    }

    fun infoString(): String {
        return "Posted by ${user.displayName} on ${dateAdded.substring(8, 10)}/${dateAdded.substring(5, 7)}/${dateAdded.substring(0, 4)}"
    }

    fun commentString(): String {
        if(noComments == 1)
            return "$noComments Comment"
        return "$noComments Comments"
    }

    fun likeString(): String {
        if(likes == 1)
            return "$likes Like"
        return "$likes Likes"
    }

    fun loadBoard(board: Board) : Post {
        _board = board
        return this
    }

    fun loadComments(comments: List<Comment>) : Post {
        _comments = comments as MutableList<Comment>
        return this
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