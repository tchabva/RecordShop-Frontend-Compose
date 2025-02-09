package uk.udemy.recordshop.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector


data class TopLevelRoute<T: Any> (val name: String, val route: T, val icon: ImageVector)

val topLevelRoute = listOf(
    TopLevelRoute("Home", Tabs.Home, Icons.Default.Home),
    TopLevelRoute("Artists", Tabs.Artists, Icons.Default.Person),
    TopLevelRoute("Genres", Tabs.Genres, Icons.Default.Info),
)