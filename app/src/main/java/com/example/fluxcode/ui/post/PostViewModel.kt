package com.example.fluxcode.ui.post

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fluxcode.domain.Post
import com.example.fluxcode.network.CodeApi
import com.example.fluxcode.network.dtos.CreateCommentDTO
import com.example.fluxcode.utils.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostViewModel(app: Application, post: Post) : ViewModel(){
    private val app = app

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> get() = _post

    val comment = MutableLiveData<String>()

    init {
        _post.value = post
    }

    fun likePost(){
        if(UserService.loggedIn){
            GlobalScope.launch(Dispatchers.Main) {
                try{
                    val response = CodeApi.retrofitService.likePost(post.value!!.id, "Bearer ${UserService.token.value}")

                    if(response.isSuccessful) {
                        refresh()
                    }else{
                        throw Exception("${response.code()}: ${response.message()}")
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }else{
            Toast.makeText(app, "Create an account or log in to be able to interact with posts", Toast.LENGTH_LONG).show()
        }
    }

    fun comment(){
        if(UserService.loggedIn){
            if(comment.value.isNullOrBlank())
                Toast.makeText(app, "Please enter text before clicking this button.", Toast.LENGTH_LONG).show()
            else
                GlobalScope.launch(Dispatchers.Main) {
                    try{
                        val response = CodeApi.retrofitService.createComment(CreateCommentDTO(post.value!!.id, comment.value!!), "Bearer ${UserService.token.value}")

                        if(response.isSuccessful) {
                            refresh()
                            comment.value = ""
                        }else{
                            throw Exception("${response.code()}: ${response.message()}")
                        }
                    }catch (e: Exception){
                        e.printStackTrace()
                        Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }else{
            Toast.makeText(app, "Create an account or log in to be able to interact with posts", Toast.LENGTH_LONG).show()
        }
    }

    private fun refresh(){
        GlobalScope.launch(Dispatchers.Main) {
            try{
                val response = CodeApi.retrofitService.getPostById(post.value!!.id)

                if(response.isSuccessful) {
                    _post.value = response.body()?.toDomain()
                }else{
                    throw Exception("${response.code()}: ${response.message()}")
                }
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}