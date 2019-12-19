package com.example.fluxcode.network.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fluxcode.network.persistence.dbos.CommentDBO
import com.example.fluxcode.network.persistence.dbos.PostDBO
import com.example.fluxcode.network.persistence.dbos.PostWithUserAndBoard
import com.example.fluxcode.network.persistence.dbos.UserDBO

@Dao
interface PostDAO {
    @Transaction
    @Query("SELECT * FROM PostDBO JOIN UserDBO ON PostDBO.userIdF = UserDBO.userId JOIN BoardDBO ON PostDBO.boardIdF = BoardDBO.boardId")
    fun getPosts() : LiveData<List<PostWithUserAndBoard>>

    @Query("SELECT * FROM PostDBO WHERE PostDBO.postId = :id")
    fun getPostById(id: Int) : LiveData<PostDBO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(vararg post: PostDBO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(vararg comment: CommentDBO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg user: UserDBO)
}