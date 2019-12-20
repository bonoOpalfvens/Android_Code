package com.example.fluxcode.ui.post

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fluxcode.domain.Post
import com.example.fluxcode.network.persistence.getDatabase
import com.example.fluxcode.network.persistence.repositories.PostRepository
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

class PostViewModel(app: Application, post: Post) : ViewModel(){
    private val app = app

    // database connection
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = getDatabase(app)
    private val postRepository = PostRepository(database)

    // fields
    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> get() = _post

    // input fields
    val comment = MutableLiveData<String>()

    init {
        // Init safeArgs arguments
        _post.value = post
    }

    fun likePost() {
        viewModelScope.launch {
            try{
                postRepository.likePost(post.value!!.id)
                refresh()
            }catch (e: SecurityException){
                Toast.makeText(app, "Create an account or log in to be able to interact with posts", Toast.LENGTH_LONG).show()
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun comment(){
        viewModelScope.launch {
            try{
                val commentS = comment.value?.trim() ?: ""
                if(commentS.length < 2 || commentS.length > 250)
                    throw IllegalArgumentException()

                postRepository.postComment(post.value!!.id, commentS)
                refresh()
                // clear comment
                comment.value = ""
            }catch(e: SecurityException){
                Toast.makeText(app, "Create an account or log in to be able to interact with posts", Toast.LENGTH_LONG).show()
            }catch(e: IllegalArgumentException){
                Toast.makeText(app, "A comment must have between 2 and 250 characters.", Toast.LENGTH_LONG).show()
            }catch(e: Exception){
                e.printStackTrace()
                Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // reload post
    private fun refresh(){
        viewModelScope.launch {
            try{
                postRepository.loadPost(post.value!!.id)
                // refresh selectedPost
                _post.value = postRepository.selectedPost.value
                postRepository.selectedPost.value = null
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // clear all jobs on clear
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}