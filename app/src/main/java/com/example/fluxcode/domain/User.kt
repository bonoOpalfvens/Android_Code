package com.example.fluxcode.domain

import androidx.core.util.PatternsCompat
import java.io.Serializable
import java.util.regex.Pattern

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

    private var _username: String = ""
    var username: String
        get() = _username
        set(value) {
            if (value.isBlank())
                throw IllegalArgumentException("Username can't be empty")
            if (value.length > 30)
                throw IllegalArgumentException("Username can't exceed 30 characters.")
            if (!Pattern.matches("^[A-Za-z0-9_-]+\$", value))
                throw IllegalArgumentException("Invalid username")

            _username = value
        }

    private var _displayName: String = ""
    var displayName: String
        get() = _displayName
        set(value) {
            if (value.isBlank())
                throw IllegalArgumentException("DisplayName can't be empty")
            if (value.length > 30)
                throw IllegalArgumentException("DisplayName can't exceed 30 characters.")
            if (!Pattern.matches("^[a-zA-Z0-9_àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]+\$", value))
                throw IllegalArgumentException("Invalid displayName")

            _displayName = value
        }

    private var _avatar: String = "https://i.imgur.com/bMxp0CX.png"
    var avatar: String
        get() = _avatar
        set(value) {
            if(value.isBlank())
                throw IllegalArgumentException("Avatar can't be empty")
            if(!PatternsCompat.WEB_URL.matcher(value).matches())
                throw IllegalArgumentException("Invalid url provided for avatar")

            _avatar = value
        }

    private var _email: String = ""
    var email: String
        get() = _email
        set(value) {
            if(value.isBlank())
                throw IllegalArgumentException("Email can't be empty")
            if(!PatternsCompat.EMAIL_ADDRESS.matcher(value).matches())
                throw IllegalArgumentException("Invalid email")

            _email = value
        }

    val emailIsPrivate: Boolean

    private var _firstName: String = ""
    var firstName: String
        get() = _firstName
        set(value) {
            if(value.length > 40)
                throw IllegalArgumentException("First name can't exceed 40 characters")
            if (value.isNotEmpty() && !Pattern.matches("^[a-zA-Z0-9_àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+\$", value))
                throw IllegalArgumentException("Invalid first name")

            _firstName = value.trim()
        }

    private var _lastName: String = ""
    var lastName: String
        get() = _lastName
        set(value) {
            if(value.length > 40)
                throw IllegalArgumentException("Last name can't exceed 40 characters")
            if (value.isNotEmpty() && !Pattern.matches("^[a-zA-Z0-9_àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+\$", value))
                throw IllegalArgumentException("Invalid last name")

            _lastName = value.trim()
        }

    private var _description: String = ""
    var description: String
        get() = _description
        set(value) {
            if(value.length > 250)
                throw IllegalArgumentException("Description can't exceed 250 characters")

            _description = value.trim()
        }

    private var _github: String = ""
    var github: String
        get() = _github
        set(value) {
            if(value.isNotBlank() && !PatternsCompat.WEB_URL.matcher(value).matches())
                throw IllegalArgumentException("Invalid url provided for github account")

            _github = value.trim()
        }

    private var _noLikedPosts: Int = 0
    var noLikedPosts: Int
        get() = _noLikedPosts
        set(value) {
            if(value < 0)
                throw IllegalArgumentException("# of liked posts can't be negative")

            _noLikedPosts = value
        }

    private var _noCreatedPosts: Int = 0
    var noCreatedPosts: Int
        get() = _noCreatedPosts
        set(value) {
            if(value < 0)
                throw IllegalArgumentException("# of created posts can't be negative")

            _noCreatedPosts = value
        }

    private var _noCreatedComments: Int = 0
    var noCreatedComments: Int
        get() = _noCreatedComments
        set(value) {
            if(value < 0)
                throw IllegalArgumentException("# of created comments can't be negative")

            _noCreatedComments = value
        }

    private var _boards = mutableListOf<Board>()
    val boards: List<Board> get() = _boards
}