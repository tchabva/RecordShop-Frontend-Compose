package uk.udemy.recordshop.ui

/*
Note: This ensures that we are always consistent route names for the Screens
 */
sealed class Screen(val route: String) {
    data object HomeScreen:Screen("homescreen")
    data object ViewAlbumScreen:Screen("viewalbumscreen")
}