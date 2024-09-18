package com.example.bookshelf.fake

import com.example.bookshelf.data.NetworkBooksRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkBookRepositoryTest {

    @Test
    fun networkBooksRepository_getBooksIdentifiers_verifyBooksIdentifiersList() = runTest {
        val repository = NetworkBooksRepository(
            bookshelfApiService = FakeBookshelfApiService()
        )
        assertEquals(FakeDataSource.bookIdentifiers, repository.getBooksFromSearch("flowers"))
    }

    @Test
    fun networkBooksRepository_getBookDetails_verifyBookDetails() = runTest {
        val repository = NetworkBooksRepository(
            bookshelfApiService = FakeBookshelfApiService()
        )
        assertEquals(FakeDataSource.fakeJsonResponse["1"], repository.getBookDetails("1"))
    }

}