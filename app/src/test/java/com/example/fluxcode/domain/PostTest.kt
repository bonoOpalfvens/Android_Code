package com.example.fluxcode.domain

import com.example.fluxcode.DummyContext
import org.junit.Assert
import org.junit.Test
import java.lang.StringBuilder

class PostTest {
    private val data = DummyContext()

    // Constructor
    @Test
    fun post_validProperties_createPost() {
        val post = Post(1, "Retrofit with kotlin", "Retrofitting refers to the addition of new technology or features to older systems, for example: power plant retrofit, improving power plant efficiency", data.user, "15/12/2019", 1, 1, true)

        Assert.assertEquals(1, post.id)
        Assert.assertEquals("Retrofit with kotlin", post.title)
        Assert.assertEquals("Retrofitting refers to the addition of new technology or features to older systems, for example: power plant retrofit, improving power plant efficiency", post.content)
        Assert.assertEquals(data.user, post.user)
        Assert.assertEquals(1, post.noComments)
        Assert.assertEquals(1, post.likes)
        Assert.assertEquals(true, post.isLiking)
    }

    // Id
    @Test(expected = IllegalArgumentException::class)
    fun post_idNegative_throwsException() {
        Post(-1, "Retrofit with kotlin", "Retrofitting refers to the addition of new technology or features to older systems, for example: power plant retrofit, improving power plant efficiency", data.user, "15/12/2019", 1, 1, true)
    }
    @Test(expected = IllegalArgumentException::class)
    fun post_idZero_throwsException() {
        Post(0, "Retrofit with kotlin", "Retrofitting refers to the addition of new technology or features to older systems, for example: power plant retrofit, improving power plant efficiency", data.user, "15/12/2019", 1, 1, true)
    }
    @Test(expected = IllegalArgumentException::class)
    fun post_idReassign_throwsException() {
        data.post[0].id = 2
    }

    // Title
    @Test(expected = IllegalArgumentException::class)
    fun post_titleBlank_throwsException() {
        data.post[0].title = "   "
    }
    @Test(expected = IllegalArgumentException::class)
    fun post_titleExceeds50Characters_throwsException() {
        data.post[0].title = "thisisareallylongnamethatexceedsfiftycharactersthisisareallylongnamethatexceedsfiftycharacters"
    }
    @Test(expected = IllegalArgumentException::class)
    fun post_titleLessThan3Characters_throwsException() {
        data.post[0].title = "th"
    }
    @Test
    fun post_titleValid_changesValue() {
        data.post[0].title = "Room data storage"
        Assert.assertEquals("Room data storage", data.post[0].title)
    }

    // Content
    @Test(expected = IllegalArgumentException::class)
    fun post_contentBlank_throwsException() {
        data.post[0].content = "                                             "
    }
    @Test(expected = IllegalArgumentException::class)
    fun post_contentExceeds10000Characters_throwsException() {
        val stringBuilder = StringBuilder()
        for (i in 0..10001) stringBuilder.append("a")
        data.post[0].content = stringBuilder.toString()
    }
    @Test(expected = IllegalArgumentException::class)
    fun post_contentLessThan30Characters_throwsException() {
        data.post[0].content = "th"
    }
    @Test
    fun post_contentValid_changesValue() {
        data.post[0].content = "Retrofitting refers to the addition of new technology or features to older systems, for example: power plant retrofit, improving power plant efficiency"
        Assert.assertEquals("Retrofitting refers to the addition of new technology or features to older systems, for example: power plant retrofit, improving power plant efficiency", data.post[0].content)
    }

    // NoComments
    @Test(expected = IllegalArgumentException::class)
    fun post_noCommentsNegative_throwsException() {
        Post(1, "Retrofit with kotlin", "Retrofitting refers to the addition of new technology or features to older systems, for example: power plant retrofit, improving power plant efficiency", data.user, "15/12/2019", -1, 1, true)
    }

    // Likes
    @Test(expected = IllegalArgumentException::class)
    fun board_likesNegative_throwsException() {
        Post(1, "Retrofit with kotlin", "Retrofitting refers to the addition of new technology or features to older systems, for example: power plant retrofit, improving power plant efficiency", data.user, "15/12/2019", 0, -1, true)
    }

    // Like()
    @Test
    fun post_likeIsLiking_decrementsLikes() {
        val post = Post(1, "Retrofit with kotlin", "Retrofitting refers to the addition of new technology or features to older systems, for example: power plant retrofit, improving power plant efficiency", data.user, "15/12/2019", 1, 1, true)

        post.like()
        Assert.assertEquals(0, post.likes)
    }
    @Test
    fun post_likeNotIsLiking_incrementLikes() {
        val post = Post(1, "Retrofit with kotlin", "Retrofitting refers to the addition of new technology or features to older systems, for example: power plant retrofit, improving power plant efficiency", data.user, "15/12/2019", 1, 0, false)

        post.like()
        Assert.assertEquals(1, post.likes)
    }
}