package uk.udemy.recordshop.ui.navigation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun TopBar(
    navController: NavController,
    artistName: String? = null,
    genreName: String? = null
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Determines whether the topAppBar is shown based on the Destination
    val showTopAppBar = Screens.screensWithTopAppBar.any {
        currentDestination?.hasRoute(it) == true
    }

    // Determines what the what the title of the TopAppBar will be
    val title = when {
        currentDestination?.hasRoute(Screens.AddOrEditAlbum::class) == true -> {
            val albumId = (navBackStackEntry?.arguments?.getLong("albumId"))
            Log.i("NavRoot", albumId.toString())
            if (albumId != 0L) "Edit Album" else "Add Album"
        }

        currentDestination?.hasRoute(Screens.Artist::class) == true -> {
            artistName ?: "Artist"
        }

        currentDestination?.hasRoute(Screens.Genre::class) == true -> {
            genreName ?: "Genre"
        }

        else -> ""
    }

    AnimatedVisibility(
        visible = showTopAppBar,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
    ) {
        CenterAlignedTopAppBar(
            title = {
                when {
                    currentDestination?.hasRoute(Screens.AddOrEditAlbum::class) == true -> {
                        Text(
                            fontWeight = FontWeight.SemiBold,
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    currentDestination?.hasRoute(Screens.Artist::class) == true ||
                            currentDestination?.hasRoute(Screens.Genre::class) == true -> {
                        Text(
                            fontWeight = FontWeight.SemiBold,
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 36.sp
                        )
                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }
        )
    }
}