package com.example.bookshelf

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class BookshelfTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun homeScreen_searchBooks_verifyBooksSearchResult() = runTest {
        composeTestRule.setContent {
            BookshelfApp()
        }
        composeTestRule.onNodeWithTag("TEXT_FIELD_SEARCH")
            .performTextInput("player")

        composeTestRule.onNodeWithTag("SEARCH_BUTTON")
            .performClick()

        advanceTimeBy(10000)

        composeTestRule.onNodeWithTag("BOOK_ITEM").assertIsDisplayed()

    }
}