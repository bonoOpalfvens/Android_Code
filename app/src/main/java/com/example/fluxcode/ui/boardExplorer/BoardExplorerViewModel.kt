package com.example.fluxcode.ui.boardExplorer

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fluxcode.domain.Board
import com.example.fluxcode.network.CodeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BoardExplorerViewModel(app: Application) : ViewModel(){
    private val app = app

    private val _boards = MutableLiveData<List<Board>>()
    val boards: LiveData<List<Board>> get() = _boards

    // safeArgs navigation
    private val _board = MutableLiveData<Board>()
    val board: LiveData<Board> get() = _board

    init {
        loadBoards()
    }

    private fun loadBoards(){
        GlobalScope.launch(Dispatchers.Main) {
            try{
                val response = CodeApi.retrofitService.getTopBoards()

                if(response.isSuccessful) {
                    _boards.value = response.body()?.map { it.toDomain() } ?: emptyList()
                }else{
                    throw Exception("${response.code()}: ${response.message()}")
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e.message == "timeout") loadBoards()
                Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // EventHandlers
    fun viewBoard(board: Board){
        GlobalScope.launch(Dispatchers.Main) {
            try{
                val response = CodeApi.retrofitService.getBoardById(board.id)

                if(response.isSuccessful) {
                    _board.value = response.body()?.toDomain()
                }else{
                    throw Exception("${response.code()}: ${response.message()}")
                }
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // SafeArgs
    fun onNavigated(){
        _board.value = null
    }
}