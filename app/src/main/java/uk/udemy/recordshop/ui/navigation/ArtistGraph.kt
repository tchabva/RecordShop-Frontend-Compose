package uk.udemy.recordshop.ui.navigation

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import uk.udemy.recordshop.ui.artists.ArtistsScreen
import uk.udemy.recordshop.ui.artists.ArtistsViewModel

fun NavGraphBuilder.artistGraph(
    navController: NavController
) {
    navigation<Tabs.Artists>(startDestination = Screens.Artists) {
        composable<Screens.Artists> {
            ArtistsScreen(
                viewModel = hiltViewModel<ArtistsViewModel>(),
                onArtistItemClicked = {
                    // TODO
                }
            )
        }
    }
}