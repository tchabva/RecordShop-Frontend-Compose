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
