package com.example.fluxcode.domain

import androidx.core.util.PatternsCompat
import java.io.Serializable

class Board : Serializable {
    constructor(id: Int, name: String, description: String, icon: String, likes: Int, noPosts: Int, isLiking: Boolean){
        this.id = id
        this.name = name
        this.description = description
        this.icon = icon
        this._likes = likes
        this.noPosts = noPosts
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

    private var _name: String = ""
    var name: String
        get() = _name
        set(value) {
            if (value.isBlank())
                throw IllegalArgumentException("Name can't be empty")
            if (value.length > 30)
                throw IllegalArgumentException("Name can't exceed 30 characters")

            _name = value
        }

    private var _description: String = ""
    var description: String
        get() = _description
        set(value) {
            if(value.isBlank())
                throw IllegalArgumentException("Description can't be empty")
            if(value.length > 100)
                throw IllegalArgumentException("Description can't exceed 100 characters")

            _description = value
        }

    private var _icon: String = "https://i.imgur.com/YajwjJS.png"
    var icon: String
        get() = _icon
        set(value) {
            if(value.isBlank())
                throw IllegalArgumentException("Icon can't be empty")
            if(!PatternsCompat.WEB_URL.matcher(value).matches())
                throw IllegalArgumentException("Invalid url provided for icon")

            _icon = value
        }

    private var _likes: Int = 0
        set(value) {
            if(value < 0)
                throw IllegalArgumentException("Likes can't be negative")

            field = value
        }
    val likes: Int get() = _likes

    private var _noPosts: Int = 0
    var noPosts: Int
        get() = _noPosts
        set(value) {
            if(value < 0)
                throw IllegalArgumentException("# of posts can't be negative")

            _noPosts = value
        }

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