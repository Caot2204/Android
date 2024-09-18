package com.example.bookshelf.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = BookSearchResultDeserializer::class)
data class BooksSearchResult(
    @SerialName(value = "items")
    val books: List<BookIdentifier>
)

@Serializable
data class BookIdentifier(
    val id: String
)

@Serializable
data class BookDetails(
    val id: String,

    @SerialName(value = "volumeInfo")
    val volumeInfo: BookVolumeInfo
)

@Serializable(with = BookVolumeInfoDeserializer::class)
data class BookVolumeInfo(
    @SerialName(value = "title")
    val title: String,

    @SerialName(value = "imageLinks")
    val imageLinks: BookImageLink
)

@Serializable
data class BookImageLink(
    val thumbnail: String
)

class BookVolumeInfoDeserializer : KSerializer<BookVolumeInfo> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("BookVolumeInfo") {
            element<String>("title")
            element<BookImageLink>("imageLinks")
        }

    override fun deserialize(decoder: Decoder): BookVolumeInfo {
        val input = decoder as? JsonDecoder ?: throw
                SerializationException("This class can be loaded" +
                        "only by Json")
        val jsonObject = input.decodeJsonElement().jsonObject
        val title = jsonObject["title"]?.jsonPrimitive?.contentOrNull ?: ""
        val imageLinksJsonObject = jsonObject["imageLinks"]?.jsonObject
        val imageLinks = if (imageLinksJsonObject != null) {
            input.json.decodeFromJsonElement<BookImageLink>(imageLinksJsonObject)
        } else {
            BookImageLink(thumbnail = "")
        }
        return BookVolumeInfo(title, imageLinks)
    }

    override fun serialize(encoder: Encoder, value: BookVolumeInfo) {
        TODO("Not yet implemented")
    }
}

class BookSearchResultDeserializer : KSerializer<BooksSearchResult> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("BooksSearchResult"){
            element<List<BookIdentifier>>("items")
        }

    override fun deserialize(decoder: Decoder): BooksSearchResult {
        val input = decoder as? JsonDecoder ?: throw
            SerializationException("This class can be loaded" +
                "only by Json")
        val jsonObject = input.decodeJsonElement().jsonObject
        val itemsJsonObject = jsonObject["items"]?.jsonArray
        val identifiers: List<BookIdentifier> = if (itemsJsonObject != null) {
            input.json.decodeFromJsonElement<List<BookIdentifier>>(itemsJsonObject)
        } else {
            emptyList()
        }
        return BooksSearchResult(identifiers)
    }

    override fun serialize(encoder: Encoder, value: BooksSearchResult) {
        TODO("Not yet implemented")
    }

}
