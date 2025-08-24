package uk.udemy.recordshop.ui.navigation.navgraphs

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import uk.udemy.recordshop.ui.navigation.Screens
import uk.udemy.recordshop.ui.navigation.Tabs
import uk.udemy.recordshop.ui.screens.genre.GenreScreen
import uk.udemy.recordshop.ui.screens.genres.GenresScreen
import uk.udemy.recordshop.ui.screens.genres.GenresViewModel

fun NavGraphBuilder.genresGraph(
    navController: NavController
) {
    navigation<Tabs.Genres>(startDestination = Screens.Genres) {
        composable<Screens.Genres> {
            GenresScreen(
                viewModel = hiltViewModel<GenresViewModel>(),
                onGenreItemClicked = { genreId ->
                    // Navigates to the Genre Screen with the genreId
                    navController.navigate(Screens.Genre(genreId))
                }
            )
        }

        composable<Screens.Genre> { backStackEntry ->
            val genreScreen: Screens.Genre = backStackEntry.toRoute()
//            val viewModel = hiltViewModel<GenreViewModel>()

            LaunchedEffect(genreScreen.genreId) {
//                viewModel.getGenreWithAlbums(genreId = genreScreen.genreId)
            }

            GenreScreen()
        }
    }
}