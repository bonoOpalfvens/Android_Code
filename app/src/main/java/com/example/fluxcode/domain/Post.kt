package com.example.fluxcode.domain

import java.io.Serializable

class Post : Serializable {
    constructor(id: Int, title: String, content: String, user: User, dateAdded: String, noComments: Int, likes: Int, isLiking: Boolean){
        this.id = id
        this.title = title
        this.content = content
        this.user = user
        this.dateAdded = dateAdded
        this.noComments = noComments
        this._likes = likes
        this._isLiking = isLiking
    }

    private var _id: Int = 0
    var id: Int
        get() = _id
        set(value) {
            if (_id != 0)
                throw IllegalArgumentException("Can't reassign id")
            if (value <= 0)
                throw IllegalArgumentException("Invalid id")

            _id = value
        }

    private var _title: String = ""
    var title: String
        get() = _title
        set(value) {
            if (value.isBlank())
                throw IllegalArgumentException("Title can't be empty")
            if (value.trim().length > 50)
                throw IllegalArgumentException("Title can't exceed 50 characters")
            if (value.trim().length < 3)
                throw IllegalArgumentException("Title can't be shorter than 3 characters")

            _title = value
        }

    private var _content: String = ""
    var content: String
        get() = _content
        set(value) {
            if (value.isBlank())
                throw IllegalArgumentException("Content can't be empty")
            if (value.trim().length > 10000)
                throw IllegalArgumentException("Content can't exceed 10000 characters")
            if (value.trim().length < 30)
                throw IllegalArgumentException("Content can't be shorter than 30 characters")

            _content = value
        }

    val user: User
    private val dateAdded: String

    private var _noComments: Int = 0
    var noComments: Int
        get() = _noComments
        set(value) {
            if(value < 0)
                throw IllegalArgumentException("# of comments can't be negative")

            _noComments = value
        }

    private var _likes: Int = 0
        set(value) {
            if(value < 0)
                throw IllegalArgumentException("Likes can't be negative")

            field = value
        }
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