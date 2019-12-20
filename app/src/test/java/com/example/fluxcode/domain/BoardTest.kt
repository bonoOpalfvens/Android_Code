package com.example.fluxcode.domain

import com.example.fluxcode.DummyContext
import org.junit.Assert
import org.junit.Test

class BoardTest {
    private val data = DummyContext()

    // Constructor
    @Test
    fun board_validProperties_createsBoard() {
        val board = Board(1, "C#", "Programming language", "https://i.imgur.com/HUBBvDr.png", 1, 0, true)

        Assert.assertEquals(1, board.id)
        Assert.assertEquals("C#", board.name)
        Assert.assertEquals("Programming language", board.description)
        Assert.assertEquals("https://i.imgur.com/HUBBvDr.png", board.icon)
        Assert.assertEquals(1, board.likes)
        Assert.assertEquals(0, board.noPosts)
        Assert.assertEquals(true, board.isLiking)
    }

    // Id
    @Test(expected = IllegalArgumentException::class)
    fun board_idNegative_throwsException() {
        Board(-1, "C#", "Programming language", "https://i.imgur.com/HUBBvDr.png", 1, 0, true)
    }
    @Test(expected = IllegalArgumentException::class)
    fun board_idZero_throwsException() {
        Board(0, "C#", "Programming language", "https://i.imgur.com/HUBBvDr.png", 1, 0, true)
    }
    @Test(expected = IllegalArgumentException::class)
    fun board_idReassign_throwsException() {
        data.boards[0].id = 2
    }

    // Name
    @Test(expected = IllegalArgumentException::class)
    fun board_nameEmpty_throwsException() {
        data.boards[0].name = ""
    }
    @Test(expected = IllegalArgumentException::class)
    fun board_nameBlank_throwsException() {
        data.boards[0].name = " "
    }
    @Test(expected = IllegalArgumentException::class)
    fun board_nameExceeds30Characters_throwsException() {
        data.boards[0].name = "thisisareallylongnamethatexceedsthirtycharacters"
    }
    @Test
    fun board_nameValid_changesValue() {
        data.boards[0].name = "Kotlin"
        Assert.assertEquals("Kotlin", data.boards[0].name)
    }

    // Description
    @Test(expected = IllegalArgumentException::class)
    fun board_descriptionEmpty_throwsException() {
        data.boards[0].description = ""
    }
    @Test(expected = IllegalArgumentException::class)
    fun board_descriptionBlank_throwsException() {
        data.boards[0].description = " "
    }
    @Test(expected = IllegalArgumentException::class)
    fun board_descriptionExceeds100Characters_throwsException() {
        data.boards[0].description = "thisisareallylongdescriptionthatexceedsonehunderedcharactersthisisareallylongdescriptionthatexceedsonehunderedcharactersthisisareallylongdescriptionthatexceedsonehunderedcharacters"
    }
    @Test
    fun board_descriptionValid_changesValue() {
        data.boards[0].description = "Development framework"
        Assert.assertEquals("Development framework", data.boards[0].description)
    }

    // Icon
    @Test(expected = IllegalArgumentException::class)
    fun board_iconEmpty_throwsException() {
        data.boards[0].icon = ""
    }
    @Test(expected = IllegalArgumentException::class)
    fun board_iconBlank_throwsException() {
        data.boards[0].icon = " "
    }
    @Test(expected = IllegalArgumentException::class)
    fun board_iconInvalidURL_throwsException() {
        data.boards[0].icon = "invalidurldotcom"
    }
    @Test
    fun board_iconValid_changesValue() {
        data.boards[0].icon = "https://i.imgur.com/0GfzcrT.png"
        Assert.assertEquals("https://i.imgur.com/0GfzcrT.png", data.boards[0].icon)
    }

    // Likes
    @Test(expected = IllegalArgumentException::class)
    fun board_likesNegative_throwsException() {
        Board(1, "C#", "Programming language", "https://i.imgur.com/HUBBvDr.png", -1, 0, true)
    }

    // noPosts
    @Test(expected = IllegalArgumentException::class)
    fun board_noPostsNegative_throwsException() {
        Board(1, "C#", "Programming language", "https://i.imgur.com/HUBBvDr.png", 0, -1, true)
    }

    // Like()
    @Test
    fun board_likeIsLiking_decrementsLikes() {
        val board = Board(1, "C#", "Programming language", "https://i.imgur.com/HUBBvDr.png", 1, 0, true)

        board.like()
        Assert.assertEquals(0, board.likes)
    }
    @Test
    fun board_likeNotIsLiking_incrementLikes() {
        val board = Board(1, "C#", "Programming language", "https://i.imgur.com/HUBBvDr.png", 0, 0, false)

        board.like()
        Assert.assertEquals(1, board.likes)
    }
}