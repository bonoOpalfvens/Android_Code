package com.example.fluxcode.network.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fluxcode.network.persistence.daos.*
import com.example.fluxcode.network.persistence.dbos.*

@Database(entities = [
    BoardDBO::class,
    CommentDBO::class,
    PostDBO::class,
    TokenDBO::class,
    UserDBO::class
], version = 1, exportSchema = false)
abstract class LocalDB: RoomDatabase() {
    abstract val boardDAO: BoardDAO
    abstract val postDAO: PostDAO
    abstract val tokenDAO: TokenDAO
}

private lateinit var INSTANCE: LocalDB

fun getDatabase(context: Context): LocalDB{
    synchronized(LocalDB::class.java){
        if(!::INSTANCE.isInitialized)
            INSTANCE = androidx.room.Room.databaseBuilder(context.applicationContext,
                LocalDB::class.java, "fluxcode").build()
    }
    return INSTANCE
}