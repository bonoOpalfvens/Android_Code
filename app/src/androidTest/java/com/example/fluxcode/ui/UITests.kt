package com.example.fluxcode.ui

import android.view.Gravity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.fluxcode.MainActivity
import com.example.fluxcode.R
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class IndividualUITests {

    // THESE TESTS NEED TO BE RAN INDEPENDENTLY

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase("fluxcode")
    }

    @Test
    fun loginFragment_invalidCredentials_showsToast() {
        // Arrange
            // load login page
        Espresso.onView(ViewMatchers.withId(R.id.login_button)).perform(ViewActions.click())
        // Act
            // insert bad credentials
        Espresso.onView(ViewMatchers.withId(R.id.editText))
            .perform(ViewActions.typeText("wrong@fluxcode.be"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.editText3))
            .perform(ViewActions.typeText("Password1*"), ViewActions.closeSoftKeyboard())
            // click login button
        Espresso.onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())
        Thread.sleep(4000)
        // Assert
            // check toast displayed
        Espresso.onView(ViewMatchers.withText("Error invalid credentials provided"))
            .inRoot(RootMatchers.withDecorView(CoreMatchers.not(activityTestRule.activity.window.decorView)))
    }

    @Test
    fun loginFragment_invalidPassword_showsToast() {
        // Arrange
            // load login page
        Espresso.onView(ViewMatchers.withId(R.id.login_button)).perform(ViewActions.click())
        // Act
            // insert email and bad password
        Espresso.onView(ViewMatchers.withId(R.id.editText))
            .perform(ViewActions.typeText("test@fluxcode.be"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.editText3))
            .perform(ViewActions.typeText("wrong"), ViewActions.closeSoftKeyboard())
            // click login button
        Espresso.onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())
        // Assert
            // check toast displayed
        Espresso.onView(ViewMatchers.withText("Invalid argument provided for password\nPassword must contain at least one of each of the following: upper case (A-Z), lower case (a-z), number (0-9) and special character (! ? | % \$ # _ - @)"))
            .inRoot(RootMatchers.withDecorView(CoreMatchers.not(activityTestRule.activity.window.decorView))).check(
                ViewAssertions.matches(ViewMatchers.isDisplayed())
            )
    }

    @Test
    fun loginFragment_invalidEmail_showsToast() {
        // Arrange
            // load login page
        Espresso.onView(ViewMatchers.withId(R.id.login_button)).perform(ViewActions.click())
        // Act
            // insert bad email
        Espresso.onView(ViewMatchers.withId(R.id.editText))
            .perform(ViewActions.typeText("aaa"), ViewActions.closeSoftKeyboard())
            // click login button
        Espresso.onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())
        // Assert
            // check toast displayed
        Espresso.onView(ViewMatchers.withText("Invalid argument provided for email"))
            .inRoot(RootMatchers.withDecorView(CoreMatchers.not(activityTestRule.activity.window.decorView))).check(
                ViewAssertions.matches(ViewMatchers.isDisplayed())
            )
    }

    @Test
    fun registerFragment_invalidEmail_showsToast() {
        // Arrange
            // load register page
        Espresso.onView(ViewMatchers.withId(R.id.register_button)).perform(ViewActions.click())
        // Act
            // insert bad email
        Espresso.onView(ViewMatchers.withId(R.id.editText))
            .perform(ViewActions.typeText("aaa"), ViewActions.closeSoftKeyboard())
            // click register button
        Espresso.onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())
        // Assert
            // check toast displayed
        Espresso.onView(ViewMatchers.withText("Invalid argument provided for email"))
            .inRoot(RootMatchers.withDecorView(CoreMatchers.not(activityTestRule.activity.window.decorView))).check(
                ViewAssertions.matches(ViewMatchers.isDisplayed())
            )
    }

    @Test
    fun registerFragment_invalidUsername_showsToast() {
        // Arrange
            // load register page
        Espresso.onView(ViewMatchers.withId(R.id.register_button)).perform(ViewActions.click())
        // Act
            // insert email and bad username
        Espresso.onView(ViewMatchers.withId(R.id.editText))
            .perform(ViewActions.typeText("test@fluxcode.be"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.editText2))
            .perform(ViewActions.typeText("';';"), ViewActions.closeSoftKeyboard())
            // click register button
        Espresso.onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())
        // Assert
            // check toast displayed
        Espresso.onView(ViewMatchers.withText("Invalid argument provided for username\nUsernames can only contain the following characters: upper case (A-Z), lower case (a-z), number (0-9), underscores and hyphens"))
            .inRoot(RootMatchers.withDecorView(CoreMatchers.not(activityTestRule.activity.window.decorView))).check(
                ViewAssertions.matches(ViewMatchers.isDisplayed())
            )
    }

    @Test
    fun registerFragment_invalidPassword_showsToast() {
        // Arrange
            // load register page
        Espresso.onView(ViewMatchers.withId(R.id.register_button)).perform(ViewActions.click())
        // Act
            // insert email, username and bad password
        Espresso.onView(ViewMatchers.withId(R.id.editText))
            .perform(ViewActions.typeText("test@fluxcode.be"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.editText2))
            .perform(ViewActions.typeText("test"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.editText3))
            .perform(ViewActions.typeText("bad"), ViewActions.closeSoftKeyboard())
            // click register button
        Espresso.onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())
        // Assert
            // check toast displayed
        Espresso.onView(ViewMatchers.withText("Invalid argument provided for password\nPassword must contain at least one of each of the following: upper case (A-Z), lower case (a-z), number (0-9) and special character (! ? | % \$ # _ - @)"))
            .inRoot(RootMatchers.withDecorView(CoreMatchers.not(activityTestRule.activity.window.decorView))).check(
                ViewAssertions.matches(ViewMatchers.isDisplayed())
            )
    }

    @Test
    fun registerFragment_passwordsNotMatching_showsToast() {
        // Arrange
            // load register page
        Espresso.onView(ViewMatchers.withId(R.id.register_button)).perform(ViewActions.click())
        // Act
            // insert email, username, password and bad passwordConfirmation
        Espresso.onView(ViewMatchers.withId(R.id.editText))
            .perform(ViewActions.typeText("test@fluxcode.be"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.editText2))
            .perform(ViewActions.typeText("test"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.editText3))
            .perform(ViewActions.typeText("Password1*"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.editText4))
            .perform(ViewActions.typeText("Password2*"), ViewActions.closeSoftKeyboard())
            // click register button
        Espresso.onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())
        // Assert
            // check toast displayed
        Espresso.onView(ViewMatchers.withText("Passwords don't match"))
            .inRoot(RootMatchers.withDecorView(CoreMatchers.not(activityTestRule.activity.window.decorView))).check(
                ViewAssertions.matches(ViewMatchers.isDisplayed())
            )
    }

    @Test
    fun loginFragment_successfulLogin() {
        // Act
        Espresso.onView(ViewMatchers.withId(R.id.login_button)).perform(ViewActions.click())
        // Assert
            // insert correct email and password
        Espresso.onView(ViewMatchers.withId(R.id.editText))
            .perform(ViewActions.typeText("test@fluxcode.be"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.editText3))
            .perform(ViewActions.typeText("Password1*"), ViewActions.closeSoftKeyboard())
            // click login button
        Espresso.onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())
        Thread.sleep(2000L)
    }

    @Test
    fun navigation_navigatesToMenuItems() {
        // Home (Button) -> Register
        // Act
        Espresso.onView(ViewMatchers.withId(R.id.register_button)).perform(ViewActions.click())
        // Assert
        Espresso.onView(ViewMatchers.withId(R.id.titleRegister))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Home
        // Arrange
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
            .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())
        // Act
        Espresso.onView(ViewMatchers.withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.nav_home))
        // Assert
        Espresso.onView(ViewMatchers.withId(R.id.login_button))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Home (Button) -> Login
        // Act
        Espresso.onView(ViewMatchers.withId(R.id.login_button)).perform(ViewActions.click())
        // Assert
        Espresso.onView(ViewMatchers.withId(R.id.titleLogin))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Boards
        // Arrange
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
            .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())
        // Act
        Espresso.onView(ViewMatchers.withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.nav_boards))
        // Assert
        Espresso.onView(ViewMatchers.withId(R.id.boardExplorerList))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Login
        // Arrange
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
            .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())
        // Act
        Espresso.onView(ViewMatchers.withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.nav_login))
        // Assert
        Espresso.onView(ViewMatchers.withId(R.id.titleLogin))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Register
        // Arrange
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
            .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())
        // Act
        Espresso.onView(ViewMatchers.withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.nav_register))
        // Assert
        Espresso.onView(ViewMatchers.withId(R.id.titleRegister))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}