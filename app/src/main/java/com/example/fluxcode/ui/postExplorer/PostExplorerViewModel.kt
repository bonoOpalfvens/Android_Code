package com.example.fluxcode.ui.postExplorer

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fluxcode.domain.Board
import com.example.fluxcode.domain.Post
import com.example.fluxcode.network.persistence.getDatabase
import com.example.fluxcode.network.persistence.repositories.BoardRepository
import com.example.fluxcode.network.persistence.repositories.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class PostExplorerViewModel(app: Application, board: Board) : ViewModel(){
    private val app = app
    val board = board

    // database connection
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = getDatabase(app)
    private val boardRepository = BoardRepository(database)
    private val postRepository = PostRepository(database)

    // safeArgs navigation
    val post: LiveData<Post> get() = postRepository.selectedPost

    // fields
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts

    init {
        // Init safeArgs arguments
        _posts.value = board.posts
    }

    // EventHandlers
    fun viewPost(post: Post){
        viewModelScope.launch {
            try{
                postRepository.loadPost(post.id)
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun likePost(post: Post){
        viewModelScope.launch {
            try{
                postRepository.likePost(post.id)
                postRepository.refreshPosts()
            }catch (e: SecurityException){
                Toast.makeText(app, "Create an account or log in to be able to interact with posts", Toast.LENGTH_LONG).show()
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // SafeArgs
    fun onNavigated(){
        postRepository.selectedPost.value = null
    }

    // clear all jobs on clear
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}