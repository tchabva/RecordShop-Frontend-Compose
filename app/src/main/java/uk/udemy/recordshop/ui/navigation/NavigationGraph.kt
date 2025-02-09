package uk.udemy.recordshop.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import uk.udemy.recordshop.ui.addOrEditAlbum.AddOrEditAlbumScreen

// The Root NavGraph
@Composable
fun NavigationGraph(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: Any = Tabs.Home,
    innerPadding: PaddingValues
) = NavHost(
    modifier = modifier.padding(innerPadding),
    navController = navController,
    startDestination = startDestination

){

    // The nested Home Tab NavGraph
    homeGraph(
        navController = navController
    )

    // The nested Artists Tab NavGraph
    artistGraph(
        navController = navController
    )

    // The nested Genres Tab NavGraph
    genresGraph(
        navController = navController
    )

    // For navigating to the Add Album Screen from the HomeTab
    composable<Screens.AddOrEditAlbum> {
        AddOrEditAlbumScreen()
    }
}