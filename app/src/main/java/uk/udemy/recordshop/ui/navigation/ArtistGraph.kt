package uk.udemy.recordshop.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import uk.udemy.recordshop.ui.artist.ArtistScreen
import uk.udemy.recordshop.ui.artist.ArtistViewModel
import uk.udemy.recordshop.ui.artists.ArtistsScreen
import uk.udemy.recordshop.ui.artists.ArtistsViewModel

fun NavGraphBuilder.artistGraph(
    navController: NavController
) {
    navigation<Tabs.Artists>(startDestination = Screens.Artists) {
        composable<Screens.Artists> {
            ArtistsScreen(
                viewModel = hiltViewModel<ArtistsViewModel>(),
                onArtistItemClicked = {artistId ->
                    // Navigates to the Artist Screen with the artistId
                    navController.navigate(Screens.Artist(artistId))
                }
            )
        }

        composable<Screens.Artist> {backStackEntry ->
            val artistScreen: Screens.Artist = backStackEntry.toRoute()
            val viewModel = hiltViewModel<ArtistViewModel>()

            LaunchedEffect(artistScreen.artistId) {
                viewModel.getArtistWithAlbums(artistId = artistScreen.artistId)
            }

            ArtistScreen(
                viewModel = viewModel,
                onAlbumItemClick = { albumId ->
                    navController.navigate(Screens.ViewAlbum(albumId))
                }
            )
        }
    }
}