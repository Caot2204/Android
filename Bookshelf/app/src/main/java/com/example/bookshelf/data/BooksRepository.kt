package com.example.bookshelf.data

import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.model.BookIdentifier
import com.example.bookshelf.network.BookshelfApiService

interface BooksRepository {
    suspend fun getBooksFromSearch(textInBook: String): List<BookIdentifier>
    suspend fun getBookDetails(bookId: String): BookDetails
}

class NetworkBooksRepository(
    private val bookshelfApiService: BookshelfApiService
) : BooksRepository {

    override suspend fun getBooksFromSearch(textInBook: String): List<BookIdentifier> =
        bookshelfApiService.getBooks(textInBook).books

    override suspend fun getBookDetails(bookId: String): BookDetails =
        bookshelfApiService.getBookDetails(bookId)

}