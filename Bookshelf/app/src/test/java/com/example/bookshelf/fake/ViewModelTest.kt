package com.example.bookshelf.fake

import com.example.bookshelf.fake.rules.TestDispatcherRule
import com.example.bookshelf.ui.BookshelfUiState
import com.example.bookshelf.ui.BookshelfViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ViewModelTest {

    @get: Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun bookshelfViewModel_getBooks_verifyBookshelfUiStateSuccess() = runTest {
        val bookshelfViewModel = BookshelfViewModel(
            booksRepository = FakeNetworkBooksRepository()
        )
        assertEquals(
            BookshelfUiState.Success(bookEntered = "flower", books = FakeDataSource.bookList),
            bookshelfViewModel.bookshelfUiState
        )
    }
}