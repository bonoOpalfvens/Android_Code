package com.example.fluxcode.ui.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fluxcode.domain.Post
import com.example.fluxcode.network.CodeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel(app: Application) : ViewModel(){
    private val app = app

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts

    init {
        loadPosts()
    }

    private fun loadPosts(){
        GlobalScope.launch(Dispatchers.Main) {
            try{
                val response = CodeApi.retrofitService.getPosts()

                if(response.isSuccessful) {
                    _posts.value = response.body()?.map { it.toDomain() } ?: emptyList()
                }else{
                    throw Exception("${response.code()}: ${response.message()}")
                }
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // EventHandlers
    fun onClick(post: Post){
        return
    }
}