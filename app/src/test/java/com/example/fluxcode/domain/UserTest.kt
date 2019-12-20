package com.example.fluxcode.domain

import com.example.fluxcode.DummyContext
import org.junit.Assert
import org.junit.Test
import java.lang.StringBuilder

class UserTest {
    private val data = DummyContext()

    // Constructor
    @Test
    fun user_validProperties_createsUser() {
        val user = User(1, "testUser", "testUser", "https://i.imgur.com/bMxp0CX.png", "test2@fluxcode.be", false, "", "", "An avid programming looking to contribute", "", 2, 2, 1)

        Assert.assertEquals(1, user.id)
        Assert.assertEquals("testUser", user.username)
        Assert.assertEquals("testUser", user.displayName)
        Assert.assertEquals("https://i.imgur.com/bMxp0CX.png", user.avatar)
        Assert.assertEquals("test2@fluxcode.be", user.email)
        Assert.assertEquals(false, user.emailIsPrivate)
        Assert.assertEquals("", user.firstName)
        Assert.assertEquals("", user.lastName)
        Assert.assertEquals("An avid programming looking to contribute", user.description)
        Assert.assertEquals("", user.github)
        Assert.assertEquals(2, user.noLikedPosts)
        Assert.assertEquals(2, user.noCreatedPosts)
        Assert.assertEquals(1, user.noCreatedComments)
    }

    // Id
    @Test(expected = IllegalArgumentException::class)
    fun user_idNegative_throwsException() {
        User(-1, "testUser", "testUser", "https://i.imgur.com/bMxp0CX.png", "test2@fluxcode.be", false, "", "", "An avid programming looking to contribute", "", 2, 2, 1)
    }
    @Test(expected = IllegalArgumentException::class)
    fun user_idZero_throwsException() {
        User(0, "testUser", "testUser", "https://i.imgur.com/bMxp0CX.png", "test2@fluxcode.be", false, "", "", "An avid programming looking to contribute", "", 2, 2, 1)
    }
    @Test(expected = IllegalArgumentException::class)
    fun user_idReassign_throwsException() {
        data.user.id = 2
    }

    // Username
    @Test(expected = IllegalArgumentException::class)
    fun user_usernameEmpty_throwsException() {
        data.user.username = ""
    }
    @Test(expected = IllegalArgumentException::class)
    fun user_usernameBlank_throwsException() {
        data.user.username = " "
    }
    @Test(expected = IllegalArgumentException::class)
    fun user_usernameExceeds30Characters_throwsException() {
        data.user.username = "thisisareallylongnamethatexceedsthirtycharacters"
    }
    @Test(expected = IllegalArgumentException::class)
    fun user_usernameInvalidCharacters_throwsException() {
        data.user.username = "$%#@#@"
    }
    @Test
    fun user_usernameValid_changesValue() {
        data.user.username = "testUser3"
        Assert.assertEquals("testUser3", data.user.username)
    }

    // DisplayName
    @Test(expected = IllegalArgumentException::class)
    fun user_displayNameEmpty_throwsException() {
        data.user.displayName = ""
    }
    @Test(expected = IllegalArgumentException::class)
    fun user_displayNameBlank_throwsException() {
        data.user.displayName = " "
    }
    @Test(expected = IllegalArgumentException::class)
    fun user_displayNameExceeds30Characters_throwsException() {
        data.user.displayName = "thisisareallylongnamethatexceedsthirtycharacters"
    }
    @Test(expected = IllegalArgumentException::class)
    fun user_displayNameInvalidCharacters_throwsException() {
        data.user.displayName = "$%#@#@"
    }
    @Test
    fun user_displayNameValid_changesValue() {
        data.user.displayName = "testUser3ęèéê"
        Assert.assertEquals("testUser3ęèéê", data.user.displayName)
    }

    // Avatar
    @Test(expected = IllegalArgumentException::class)
    fun user_avatarEmpty_throwsException() {
        data.user.avatar = ""
    }
    @Test(expected = IllegalArgumentException::class)
    fun user_avatarBlank_throwsException() {
        data.user.avatar = " "
    }
    @Test(expected = IllegalArgumentException::class)
    fun user_avatarInvalidURL_throwsException() {
        data.user.avatar = "invalidurldotcom"
    }
    @Test
    fun user_avatarValid_changesValue() {
        data.user.avatar = "https://i.imgur.com/0GfzcrT.png"
        Assert.assertEquals("https://i.imgur.com/0GfzcrT.png", data.user.avatar)
    }

    // Email
    @Test(expected = IllegalArgumentException::class)
    fun user_emailEmpty_throwsException() {
        data.user.email = ""
    }
    @Test(expected = IllegalArgumentException::class)
    fun user_emailBlank_throwsException() {
        data.user.email = " "
    }
    @Test(expected = IllegalArgumentException::class)
    fun user_emailInvalidEmail_throwsException() {
        data.user.email = "invalidemail@dot@com"
    }
    @Test
    fun user_emailValid_changesValue() {
        data.user.email = "test@example.com"
        Assert.assertEquals("test@example.com", data.user.email)
    }

    // FirstName
    @Test(expected = IllegalArgumentException::class)
    fun user_firstNameExceeds30Characters_throwsException() {
        data.user.firstName = "thisisareallylongnamethatexceedsthirtycharacters"
    }
    @Test(expected = IllegalArgumentException::class)
    fun user_firstNameInvalidCharacters_throwsException() {
        data.user.firstName = "$%#@#@"
    }
    @Test
    fun user_firstNameValid_changesValue() {
        data.user.firstName = "testUser"
        Assert.assertEquals("testUser", data.user.firstName)
    }
    @Test
    fun user_firstNameEmpty_changesValue() {
        data.user.firstName = ""
        Assert.assertEquals("", data.user.firstName)
    }
    @Test
    fun user_firstNameBlank_changesValue() {
        data.user.firstName = " "
        Assert.assertEquals("", data.user.firstName)
    }

    // LastName
    @Test(expected = IllegalArgumentException::class)
    fun user_lastNameExceeds30Characters_throwsException() {
        data.user.lastName = "thisisareallylongnamethatexceedsthirtycharacters"
    }
    @Test(expected = IllegalArgumentException::class)
    fun user_lastNameInvalidCharacters_throwsException() {
        data.user.lastName = "$%#@#@"
    }
    @Test
    fun user_lastNameValid_changesValue() {
        data.user.lastName = "testUser"
        Assert.assertEquals("testUser", data.user.lastName)
    }
    @Test
    fun user_lastNameEmpty_changesValue() {
        data.user.lastName = ""
        Assert.assertEquals("", data.user.lastName)
    }
    @Test
    fun user_lastNameBlank_changesValue() {
        data.user.lastName = " "
        Assert.assertEquals("", data.user.lastName)
    }

    // Description
    @Test(expected = IllegalArgumentException::class)
    fun user_descriptionExceeds250Characters_throwsException() {
        val stringBuilder = StringBuilder()
        for (i in 0..251) stringBuilder.append("a")
        data.user.lastName = stringBuilder.toString()
    }
    @Test
    fun user_descriptionValid_changesValue() {
        data.user.description = "This is a user description"
        Assert.assertEquals("This is a user description", data.user.description)
    }
    @Test
    fun user_descriptionBlank_changesValue() {
        data.user.description = " "
        Assert.assertEquals("", data.user.description)
    }

    // Github
    @Test(expected = IllegalArgumentException::class)
    fun user_githubInvalidURL_throwsException() {
        data.user.github = "invalidurldotcom"
    }
    @Test
    fun user_githubBlank_changesValue() {
        data.user.github = " "
        Assert.assertEquals("", data.user.github)
    }
    @Test
    fun user_githubValid_changesValue() {
        data.user.github = "https://github.com/bonoOpalfvens"
        Assert.assertEquals("https://github.com/bonoOpalfvens", data.user.github)
    }

    // NoLikedPosts
    @Test(expected = IllegalArgumentException::class)
    fun user_noLikedPostsNegative_throwsException() {
        User(1, "testUser", "testUser", "https://i.imgur.com/bMxp0CX.png", "test2@fluxcode.be", false, "", "", "An avid programming looking to contribute", "", -2, 2, 1)
    }

    // NoCreatedPosts
    @Test(expected = IllegalArgumentException::class)
    fun user_noCreatedPostsNegative_throwsException() {
        User(1, "testUser", "testUser", "https://i.imgur.com/bMxp0CX.png", "test2@fluxcode.be", false, "", "", "An avid programming looking to contribute", "", 2, -2, 1)
    }

    // NoCreatedComments
    @Test(expected = IllegalArgumentException::class)
    fun user_noCreatedCommentsNegative_throwsException() {
        User(1, "testUser", "testUser", "https://i.imgur.com/bMxp0CX.png", "test2@fluxcode.be", false, "", "", "An avid programming looking to contribute", "", 2, 2, -1)
    }
}