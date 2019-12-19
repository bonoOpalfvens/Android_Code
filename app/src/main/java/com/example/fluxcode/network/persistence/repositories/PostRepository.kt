package com.example.fluxcode.network.persistence.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.fluxcode.domain.Post
import com.example.fluxcode.network.CodeApi
import com.example.fluxcode.network.persistence.LocalDB
import com.example.fluxcode.utils.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository(private val database: LocalDB) {
    val posts: LiveData<List<Post>> =
        Transformations.map(database.postDAO.getPosts()){ it?.map { p -> p.toDomain() } }

    val selectedPost = MutableLiveData<Post>()

    suspend fun refreshPosts() {
        withContext(Dispatchers.IO){
            val response = CodeApi.retrofitService.getPosts()

            if(response.isSuccessful) {
                val posts = response.body()
                posts?.forEach {
                    database.boardDAO.insertBoard(it.board.toDBO())
                    database.postDAO.insertUser(it.user.toDBO())
                    database.postDAO.insertPost(it.toDBO())
                }
            }else{
                throw Exception("${response.code()}: ${response.message()}")
            }
        }
    }

    suspend fun loadPost(id: Int) {
        if(!UserService.loggedIn)
            throw SecurityException()

        withContext(Dispatchers.IO){
            val response = CodeApi.retrofitService.getPostById(id)

            if(response.isSuccessful) {
                val post = response.body()

                database.postDAO.insertUser(post!!.user.toDBO())
                database.boardDAO.insertBoard(post.board.toDBO())
                post.comments.forEach {
                    database.postDAO.insertComment(it.toDBO(post.id))
                }
                database.postDAO.insertPost(post.toDBO())

                selectedPost.value = post.toDomain()
            }else{
                throw Exception("${response.code()}: ${response.message()}")
            }
        }
    }

    suspend fun likePost(id: Int) {
        withContext(Dispatchers.IO){
            val response = CodeApi.retrofitService.likePost(id, "Bearer ${UserService.token.value}")

            if(response.isSuccessful) {
                refreshPosts()
            }else{
                throw Exception("${response.code()}: ${response.message()}")
            }
        }
    }
}