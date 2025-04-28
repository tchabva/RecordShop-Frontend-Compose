package uk.udemy.recordshop.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector


data class TopLevelRoute<T: Any> (val name: String, val route: T, val icon: ImageVector)
// TODO add clarity for which icon is selected
val topLevelRoute = listOf(
    TopLevelRoute("Home", Tabs.Home, Icons.Default.Home),
    TopLevelRoute("Artists", Tabs.Artists, Icons.Default.Person),
    TopLevelRoute("Genres", Tabs.Genres, Icons.Default.MusicNote),
)