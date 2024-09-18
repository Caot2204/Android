package com.example.bookshelf.network

import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.model.BooksSearchResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookshelfApiService {

    @GET("volumes")
    suspend fun getBooks(
        @Query("q") textInBook: String
    ): BooksSearchResult

    @GET("volumes/{id}")
    suspend fun getBookDetails(
        @Path("id") bookId: String
    ): BookDetails

}