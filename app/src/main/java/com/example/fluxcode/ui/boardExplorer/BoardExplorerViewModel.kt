package com.example.fluxcode.ui.boardExplorer

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fluxcode.domain.Board
import com.example.fluxcode.network.persistence.getDatabase
import com.example.fluxcode.network.persistence.repositories.BoardRepository
import kotlinx.coroutines.*

class BoardExplorerViewModel(app: Application) : ViewModel(){
    private val app = app

    // database connection
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = getDatabase(app)
    private val boardRepository = BoardRepository(database)

    // safeArgs navigation
    val board: LiveData<Board> get() = boardRepository.selectedBoard

    // fields
    val boards: LiveData<List<Board>> get() = boardRepository.boards

    init {
        loadBoards()
    }

    private fun loadBoards(){
        viewModelScope.launch {
            try{
                boardRepository.refreshBoards()
            }catch (e: Exception){
                e.printStackTrace()
                if(e.message == "timeout") loadBoards()
                Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // EventHandlers
    fun viewBoard(board: Board){
        viewModelScope.launch {
            try{
                boardRepository.loadBoardById(board.id)
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(app, "Error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // SafeArgs
    fun onNavigated(){
        boardRepository.selectedBoard.value = null
    }

    // clear all jobs on clear
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}