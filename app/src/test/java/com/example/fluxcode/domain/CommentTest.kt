package com.example.fluxcode.domain

import com.example.fluxcode.DummyContext
import org.junit.Assert
import org.junit.Test
import java.lang.StringBuilder

class CommentTest {
    private val data = DummyContext()

    // Constructor
    @Test
    fun comment_validProperties_createsComment() {
        val comment = Comment(1, data.user, "16/12/2019", "Great summary!", 0, false)

        Assert.assertEquals(1, comment.id)
        Assert.assertEquals(data.user, comment.user)
        Assert.assertEquals("Great summary!", comment.content)
        Assert.assertEquals(0, comment.likes)
        Assert.assertEquals(false, comment.isLiking)
    }

    // Id
    @Test(expected = IllegalArgumentException::class)
    fun comment_idNegative_throwsException() {
        Comment(-1, data.user, "16/12/2019", "Great summary!", 0, false)
    }
    @Test(expected = IllegalArgumentException::class)
    fun comment_idZero_throwsException() {
        Comment(0, data.user, "16/12/2019", "Great summary!", 0, false)
    }
    @Test(expected = IllegalArgumentException::class)
    fun comment_idReassign_throwsException() {
        data.comment.id = 2
    }

    // Content
    @Test(expected = IllegalArgumentException::class)
    fun comment_contentEmpty_throwsException() {
        data.comment.content = ""
    }
    @Test(expected = IllegalArgumentException::class)
    fun comment_contentBlank_throwsException() {
        data.comment.content = " "
    }
    @Test(expected = IllegalArgumentException::class)
    fun comment_contentExceeds250Characters_throwsException() {
        val stringBuilder = StringBuilder()
        for (i in 0..251) stringBuilder.append("a")
        data.comment.content = stringBuilder.toString()
    }
    @Test(expected = IllegalArgumentException::class)
    fun comment_contentLessThan2Characters_throwsException() {
        data.comment.content = "t  "
    }
    @Test
    fun comment_contentValid_changesValue() {
        data.comment.content = "Development framework"
        Assert.assertEquals("Development framework", data.comment.content)
    }

    // Likes
    @Test(expected = IllegalArgumentException::class)
    fun comment_likesNegative_throwsException() {
        Comment(0, data.user, "16/12/2019", "Great summary!", -1, false)
    }

    // Like()
    @Test
    fun comment_likeIsLiking_decrementsLikes() {
        val comment = Comment(1, data.user, "16/12/2019", "Great summary!", 1, true)

        comment.like()
        Assert.assertEquals(0, comment.likes)
    }
    @Test
    fun comment_likeNotIsLiking_incrementLikes() {
        val comment = Comment(1, data.user, "16/12/2019", "Great summary!", 0, false)

        comment.like()
        Assert.assertEquals(1, comment.likes)
    }
}