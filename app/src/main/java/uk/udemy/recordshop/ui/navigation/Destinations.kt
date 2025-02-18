package uk.udemy.recordshop.ui.navigation

import kotlinx.serialization.Serializable

/*
Note: This ensures that we are always consistent route names for the Screens
 */
// Tabs are for the sub navigation graphs
@Serializable
sealed interface Tabs {
    @Serializable
    data object Home : Tabs

    @Serializable
    data object Artists : Tabs

    @Serializable
    data object Genres : Tabs
}

// The Screens are for the each composable Screen
@Serializable
sealed interface Screens {
    @Serializable
    data object Home : Screens

    @Serializable
    data object Artists : Screens

    @Serializable
    data object Genres : Screens

    @Serializable
    data class Artist(val artistId: Long) : Screens

    @Serializable
    data class Genre(val genreId: Long) : Screens

    @Serializable
    data class ViewAlbum(val albumId: Long) : Screens

    @Serializable
    data class AddOrEditAlbum(val albumId: Long? = null) : Screens

    companion object{
        val screensWithoutBottomNav = listOf(
            ViewAlbum::class,
            AddOrEditAlbum::class
        )

        val screensWithTopAppBar = listOf(
            AddOrEditAlbum::class
        )
    }
}