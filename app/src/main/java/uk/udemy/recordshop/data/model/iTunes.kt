package uk.udemy.recordshop.data.model

data class ItunesAlbum(
    val artistName: String,
    val collectionName: String,
    var artworkUrl100: String
)

data class ItunesResponse(
    val resultCount: Int,
    val results: List<ItunesAlbum>
)