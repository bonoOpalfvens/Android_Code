package com.example.fluxcode.network.persistence.dbos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fluxcode.domain.Board

@Entity
data class BoardDBO constructor(
    @PrimaryKey
    val boardId: Int,
    val name: String,
    val description: String,
    val icon: String,
    val boardLikes: Int,
    val noPosts: Int
){
    fun toDomain() : Board {
        return Board(boardId, name, description, icon, boardLikes, noPosts, false)
    }
}