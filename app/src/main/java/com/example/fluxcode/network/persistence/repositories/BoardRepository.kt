package com.example.fluxcode.network.persistence.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.fluxcode.domain.Board
import com.example.fluxcode.network.CodeApi
import com.example.fluxcode.network.persistence.LocalDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BoardRepository(private val database: LocalDB) {
    val boards: LiveData<List<Board>> =
        Transformations.map(database.boardDAO.getTopBoards()){ it.map { b -> b.toDomain() } }

    val selectedBoard = MutableLiveData<Board>()

    suspend fun refreshBoards() {
        withContext(Dispatchers.IO){
            val response = CodeApi.retrofitService.getTopBoards()

            if(response.isSuccessful) {
                val boards = response.body()
                boards?.forEach {
                    database.boardDAO.insertBoard(it.toDBO())
                }
            }else{
                throw Exception("${response.code()}: ${response.message()}")
            }
        }
    }

    suspend fun loadBoardById(id: Int) {
        withContext(Dispatchers.IO){
            val response = CodeApi.retrofitService.getBoardById(id)

            if(response.isSuccessful) {
                val board = response.body()
                database.boardDAO.insertBoard(board!!.toDBO())
                board.posts.forEach {
                    database.postDAO.insertPost(it.toDBO(board.id))
                    database.postDAO.insertUser(it.user.toDBO())
                }
                selectedBoard.value = board.toDomain()
            }else{
                throw Exception("${response.code()}: ${response.message()}")
            }
        }
    }
}