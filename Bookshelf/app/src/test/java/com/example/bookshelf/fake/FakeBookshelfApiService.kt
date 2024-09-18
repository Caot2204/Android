package com.example.bookshelf.fake

import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.model.BooksSearchResult
import com.example.bookshelf.network.BookshelfApiService

class FakeBookshelfApiService : BookshelfApiService {
    override suspend fun getBooks(textInBook: String): BooksSearchResult {
        return BooksSearchResult(books = FakeDataSource.bookIdentifiers)
    }

    override suspend fun getBookDetails(bookId: String): BookDetails {
        return FakeDataSource.fakeJsonResponse[bookId]!!
    }
}