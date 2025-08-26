package uk.udemy.recordshop.data.model

data class Album(
    val id: Long?,
    var title: String,
    var artist: String,
    var genre: String,
    var releaseDate: String,
    var stock: Int,
    var price: Double,
    var artworkUrl: String?,
    var dateCreated: String?,
    var dateModified: String?,
)

data class ArtistAndAlbums(
    val id: Long,
    val artistName: String,
    val albums: List<Album>
)

data class ArtistDTO(
    val id: Long,
    val artistName: String
)

data class GenreDTO(
    val id: Long,
    val genre: String
)

data class GenreAndAlbums(
    val id: Long,
    val genre: String,
    val albums: List<Album>
)