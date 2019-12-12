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

    val id: Int
    val user: User
    private val dateAdded: String
    val content: String

    private var _likes: Int
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