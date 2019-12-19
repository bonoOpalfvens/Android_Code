package com.example.fluxcode.network.persistence.dbos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TokenDBO (
    @PrimaryKey
    val token: String
)