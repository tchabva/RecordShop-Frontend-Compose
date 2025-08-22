package uk.udemy.recordshop.ui.navigation.navgraphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import uk.udemy.recordshop.ui.screens.genres.GenresScreen
import uk.udemy.recordshop.ui.navigation.Screens
import uk.udemy.recordshop.ui.navigation.Tabs

fun NavGraphBuilder.genresGraph(
    navController: NavController
) {
    navigation<Tabs.Genres>(startDestination = Screens.Genres) {
        composable<Screens.Genres> {
            GenresScreen()
        }
    }
}