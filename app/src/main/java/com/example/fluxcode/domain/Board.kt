package com.example.fluxcode.domain

import java.io.Serializable

class Board : Serializable {
    constructor(id: Int, name: String, description: String, icon: String, likes: Int, noPosts: Int, isLiking: Boolean){
        this.id = id
        this.name = name
        this.description = description
        this.icon = icon
        this._likes = likes
        this._noPosts = noPosts
        this._isLiking = isLiking
    }

    val id: Int
    val name: String
    val description: String
    val icon: String

    private var _likes: Int
    val likes: Int get() = _likes

    private var _noPosts: Int
    val noPosts: Int get() = _noPosts

    private var _isLiking: Boolean
    val isLiking: Boolean get() = _isLiking

    private var _posts = mutableListOf<Post>()
    val posts: List<Post> get() = _posts

    fun postsString(): String {
        if(noPosts == 1)
            return "$noPosts Post"
        return "$noPosts Posts"
    }

    fun loadPosts(posts: List<Post>) : Board {
        _posts = posts as MutableList<Post>
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