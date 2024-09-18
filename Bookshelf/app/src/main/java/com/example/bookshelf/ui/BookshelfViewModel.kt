package com.example.bookshelf.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.model.BookDetails
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookshelfUiState {
    data class Success(
        val bookEntered: String,
        val books: List<BookDetails>
    ): BookshelfUiState
    object Loading: BookshelfUiState
    object Error: BookshelfUiState
}

class BookshelfViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {

    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    var enteredBook: String by mutableStateOf("")
        private set

    fun updateEnteredBook(userEnteredBook: String) {
        enteredBook = userEnteredBook
    }

    init {
        getBooksFromSearch("flower")
    }

    fun getBooksFromSearch(textInBook: String) {
        viewModelScope.launch {
            bookshelfUiState = BookshelfUiState.Loading
            bookshelfUiState = try {
                val booksIdentifiers = booksRepository.getBooksFromSearch(textInBook)
                val books = mutableListOf<BookDetails>()
                for(book in booksIdentifiers) {
                    books.add(booksRepository.getBookDetails(book.id))
                }
                BookshelfUiState.Success(
                    bookEntered = textInBook,
                    books = books
                )
            } catch (e: IOException) {
                BookshelfUiState.Error
            } catch (e: HttpException) {
                BookshelfUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val booksRepository = application.container.booksRepository
                BookshelfViewModel(booksRepository = booksRepository)
            }
        }
    }

}