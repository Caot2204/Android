package com.example.bookshelf.fake

import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.model.BookIdentifier
import com.example.bookshelf.model.BookImageLink
import com.example.bookshelf.model.BookVolumeInfo

object FakeDataSource {
    const val idOne = "1"
    const val titleOne = "titulo1"
    const val urlOne = "sdfjoifjoisdf"
    const val idTwo = "2"
    const val titleTwo = "titulo2"
    const val urlTwo = "sdfjoidfsgfdgfjoisdf"

    val bookIdentifiers = listOf(
        BookIdentifier(id = idOne),
        BookIdentifier(id = idTwo)
    )

    val fakeJsonResponse = mapOf(
        idOne to BookDetails(
            id = idOne,
            volumeInfo = BookVolumeInfo(
                title = titleOne,
                imageLinks = BookImageLink(
                    thumbnail = urlOne
                )
            )
        ),
        idTwo to BookDetails(
            id = idTwo,
            volumeInfo = BookVolumeInfo(
                title = titleTwo,
                imageLinks = BookImageLink(
                    thumbnail = urlTwo
                )
            )
        )
    )

    val bookList = listOf(
        BookDetails(
            id = idOne,
            volumeInfo = BookVolumeInfo(
                title = titleOne,
                imageLinks = BookImageLink(
                    thumbnail = urlOne
                )
            )
        ),
        BookDetails(
            id = idTwo,
            volumeInfo = BookVolumeInfo(
                title = titleTwo,
                imageLinks = BookImageLink(
                    thumbnail = urlTwo
                )
            )
        )
    )
}