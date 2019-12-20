package com.example.fluxcode.network.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fluxcode.network.persistence.dbos.BoardDBO

@Dao
interface BoardDAO {
    @Query("SELECT * FROM BoardDBO")
    fun getTopBoards() : LiveData<List<BoardDBO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBoard(vararg board: BoardDBO)
}