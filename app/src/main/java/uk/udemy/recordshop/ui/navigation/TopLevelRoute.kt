package uk.udemy.recordshop.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class TopLevelRoute<T : Any>(
    val name: String,
    val route: T,
    var icon: (Boolean) -> ImageVector
)

val topLevelRoute = listOf(
    TopLevelRoute(
        name = "Home",
        route = Tabs.Home,
        icon = { isSelected ->
            if (isSelected) Icons.Filled.Home else Icons.Outlined.Home
        }
    ),
    TopLevelRoute(
        name = "Artists",
        route = Tabs.Artists,
        icon = { isSelected ->
            if (isSelected) Icons.Filled.Person else Icons.Outlined.Person
        }
    ),
    TopLevelRoute(
        name = "Genres",
        route = Tabs.Genres,
        icon = { isSelected ->
            if (isSelected) Icons.Filled.MusicNote else Icons.Outlined.MusicNote
        }
    ),
)