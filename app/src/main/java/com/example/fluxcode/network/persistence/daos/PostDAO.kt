package com.example.fluxcode.network.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fluxcode.network.persistence.dbos.*

@Dao
interface PostDAO {
    @Transaction
    @Query("SELECT * FROM PostDBO JOIN UserDBO ON PostDBO.userIdF = UserDBO.userId JOIN BoardDBO ON PostDBO.boardIdF = BoardDBO.boardId")
    fun getPosts() : LiveData<List<PostWithUserAndBoard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(vararg post: PostDBO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg user: UserDBO)
}