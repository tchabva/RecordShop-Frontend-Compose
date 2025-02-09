package uk.udemy.recordshop.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import uk.udemy.recordshop.ui.artists.ArtistsScreen

fun NavGraphBuilder.artistGraph(
    navController: NavController
) {
    navigation<Tabs.Artists>(startDestination = Screens.Artists) {
        composable<Screens.Artists> {
            ArtistsScreen()
        }
    }
}