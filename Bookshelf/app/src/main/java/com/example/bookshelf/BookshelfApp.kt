package com.example.bookshelf

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.ui.BookshelfViewModel
import com.example.bookshelf.ui.screens.HomeScreen

@Composable
fun BookshelfApp() {
    Scaffold(
        topBar = { BookshelfTopAppBar() }
    ) { paddingValues ->
        val bookshelfViewModel: BookshelfViewModel = viewModel(factory = BookshelfViewModel.Factory)
        HomeScreen(
            bookshelfUiState = bookshelfViewModel.bookshelfUiState,
            enteredBook = bookshelfViewModel.enteredBook,
            retryAction = {
                bookshelfViewModel.getBooksFromSearch(bookshelfViewModel.enteredBook)
            },
            onUserBookChange = { bookshelfViewModel.updateEnteredBook(it) },
            onSearchDone = { bookshelfViewModel.getBooksFromSearch(bookshelfViewModel.enteredBook) },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge
            )
        }
    )
}