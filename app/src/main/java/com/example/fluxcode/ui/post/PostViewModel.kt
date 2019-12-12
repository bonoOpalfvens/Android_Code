package com.example.fluxcode.ui.post

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fluxcode.domain.Post

class PostViewModel(app: Application, post: Post) : ViewModel(){
    private val app = app

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> get() = _post

    init {
        _post.value = post
    }
}