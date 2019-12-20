package com.example.fluxcode.domain

import java.io.Serializable

class Comment : Serializable {
    constructor(id: Int, user: User, dateAdded: String, content: String, likes: Int, isLiking: Boolean){
        this.id = id
        this.user = user
        this.dateAdded = dateAdded
        this.content = content
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

    val user: User
    private val dateAdded: String

    private var _content: String = ""
    var content: String
        get() = _content
        set(value) {
            if(value.isBlank())
                throw IllegalArgumentException("Content can't be empty")
            if(value.length > 250)
                throw IllegalArgumentException("Content can't exceed 250 characters")
            if(value.trim().length < 2)
                throw IllegalArgumentException("Content can't have less than 2 characters")

            _content = value.trim()
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

    fun infoString(): String {
        return "Posted on ${dateAdded.substring(8, 10)}/${dateAdded.substring(5, 7)}/${dateAdded.substring(0, 4)}"
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