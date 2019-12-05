package com.example.fluxcode.domain

import java.io.Serializable
import java.util.*

class Comment : Serializable {
    constructor(id: Int, user: User, dateAdded: Date, content: String, likes: Int, isLiking: Boolean, postId: Int){
        this.id = id
        this.user = user
        this.dateAdded = dateAdded
        this.content = content
        this._likes = likes
        this._isLiking = isLiking
        this.postId = postId
    }

    val id: Int
    val user: User
    val dateAdded: Date
    val content: String
    val postId: Int

    private var _likes: Int
    val likes: Int get() = _likes

    private var _isLiking: Boolean
    val isLiking: Boolean get() = _isLiking

    fun like(){
        if(isLiking) {
            _likes--
        }else{
            _likes++
        }
        _isLiking = !isLiking
    }
}