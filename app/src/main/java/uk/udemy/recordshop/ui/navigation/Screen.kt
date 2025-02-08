package uk.udemy.recordshop.ui.navigation

import kotlinx.serialization.Serializable

/*
Note: This ensures that we are always consistent route names for the Screens
 */
@Serializable
object Home

@Serializable
object Artists

@Serializable
object Genres

@Serializable
data class Artist(val artistId: Long)

@Serializable
data class Genre(val genreId: Long)

@Serializable
data class ViewAlbum(val albumId: Long)

@Serializable
data class AddOrEditAlbum(val albumId: Long? = null)