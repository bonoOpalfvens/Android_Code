package com.example.fluxcode.ui.postExplorer

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fluxcode.domain.Board
import com.example.fluxcode.domain.Post
import com.example.fluxcode.network.CodeApi
import com.example.fluxcode.utils.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostExplorerViewModel(app: Application, board: Board) : ViewModel(){
    private val app = app
    val board = board

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts

    // safeArgs navigation
    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> get() = _post

    init {
        _posts.value = board.posts
    }

    private fun refresh(){
        GlobalScope.launch(Dispatchers.Main) {
            try{
                val response = CodeApi.retrofitService.getBoardById(board.id)

                if(response.isSuccessful) {
                    _posts.value = response.body()!!.toDomain().posts
                }else{
                    throw Exception("${response.code()}: ${response.message()}")
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e.message == "timeout") refresh()
                Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // EventHandlers
    fun viewPost(post: Post){
        GlobalScope.launch(Dispatchers.Main) {
            try{
                val response = CodeApi.retrofitService.getPostById(post.id)

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
    fun likePost(post: Post){
        if(UserService.loggedIn){
            GlobalScope.launch(Dispatchers.Main) {
                try{
                    val response = CodeApi.retrofitService.likePost(post.id, "Bearer ${UserService.token.value}")

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

    // SafeArgs
    fun onNavigated(){
        _post.value = null
    }
}