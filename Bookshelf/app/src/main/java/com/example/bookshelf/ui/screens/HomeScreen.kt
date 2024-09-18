package com.example.bookshelf.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.model.BookImageLink
import com.example.bookshelf.model.BookVolumeInfo
import com.example.bookshelf.ui.BookshelfUiState
import com.example.bookshelf.ui.theme.BookshelfTheme
import com.example.bookshelf.ui.theme.Shapes

@Composable
fun HomeScreen(
    bookshelfUiState: BookshelfUiState,
    enteredBook: String,
    retryAction: () -> Unit,
    onUserBookChange: (String) -> Unit,
    onSearchDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier)
    {
        BookSearchField(
            userBook = enteredBook,
            onUserBookChange = onUserBookChange,
            onSearchDone = onSearchDone,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        when (bookshelfUiState) {
            is BookshelfUiState.Success ->
                if (bookshelfUiState.books.isEmpty()) {
                    NotResultsScreen(modifier = Modifier.fillMaxSize())
                } else {
                    BookGrid(
                        books = bookshelfUiState.books
                    )
                }
            is BookshelfUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
            is BookshelfUiState.Error -> ErrorScreen(retryAction = retryAction, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun BookSearchField(
    userBook: String,
    onUserBookChange: (String) -> Unit,
    onSearchDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.clip(Shapes.medium)
    ) {
        TextField(
            value = userBook,
            onValueChange = onUserBookChange,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = ""
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("TEXT_FIELD_SEARCH"),
            label = {
                Text(text = stringResource(R.string.enter_book))
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearchDone() }
            )
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(dimensionResource(R.dimen.default_size)),
        painter = painterResource(R.drawable.ic_loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun NotResultsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(dimensionResource(R.dimen.default_size)),
            imageVector = Icons.Filled.Info,
            contentDescription = stringResource(R.string.not_results)
        )
        Text(text = stringResource(R.string.not_results))
    }
}

@Composable
fun BookGrid(
    books: List<BookDetails>,
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(dimensionResource(R.dimen.column_widht)),
        modifier = modifier
    ) {
        items(items = books, key = { book -> book.id }) { book ->
            BookDetailsCard(
                book = book,
                modifier = modifier
                    .padding(dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun BookDetailsCard(
    book: BookDetails,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.volumeInfo.imageLinks.thumbnail.replace("http", "https"))
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.ic_loading),
                contentDescription = book.volumeInfo.title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.heigh_thumbnail))
            )
            Text(
                text = book.volumeInfo.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .testTag("BOOK_ITEM")
            )
        }
    }
}

@Preview
@Composable
fun BookCardPreview() {
    BookshelfTheme {
        BookDetailsCard(
            book = BookDetails(
                id = "sdfdsf",
                volumeInfo = BookVolumeInfo(
                    title = "Titulo de ejemplo",
                    imageLinks = BookImageLink(
                        thumbnail = ""
                    )
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GridPreview() {
    BookshelfTheme {
        val books = List(10) {
            BookDetails(
                id = "sdfdsf",
                volumeInfo = BookVolumeInfo(
                    title = "Titulo de ejemplo",
                    imageLinks = BookImageLink(
                        thumbnail = ""
                    )
                )
            )
        }
        BookGrid(books = books)
    }
}