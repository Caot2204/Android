package com.example.bookshelf.fake

import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.model.BookIdentifier

class FakeNetworkBooksRepository : BooksRepository {
    override suspend fun getBooksFromSearch(textInBook: String): List<BookIdentifier> {
        return FakeDataSource.bookIdentifiers
    }

    override suspend fun getBookDetails(bookId: String): BookDetails {
        return FakeDataSource.fakeJsonResponse[bookId]!!
    }
}