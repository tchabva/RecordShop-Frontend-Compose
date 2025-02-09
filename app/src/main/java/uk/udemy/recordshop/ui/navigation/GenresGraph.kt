package uk.udemy.recordshop.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import uk.udemy.recordshop.ui.genres.GenresScreen

fun NavGraphBuilder.genresGraph(
    navController: NavController
) {
    navigation<Tabs.Genres>(startDestination = Screens.Genres) {
        composable<Screens.Genres>() {
            GenresScreen()
        }
    }
}