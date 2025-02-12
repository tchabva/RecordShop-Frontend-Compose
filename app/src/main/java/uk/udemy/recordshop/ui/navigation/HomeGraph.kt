package uk.udemy.recordshop.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import uk.udemy.recordshop.ui.home.HomeScreen
import uk.udemy.recordshop.ui.home.HomeViewModel
import uk.udemy.recordshop.ui.viewAlbum.ViewAlbumScreen
import uk.udemy.recordshop.ui.viewAlbum.ViewAlbumViewModel

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation<Tabs.Home>(startDestination = Screens.Home) {
        composable<Screens.Home> {
            HomeScreen(
                onAddAlbumClick = {
                    navController.navigate(Screens.AddOrEditAlbum())
                },
                viewModel = hiltViewModel<HomeViewModel>(),
                // Navigates to the ViewAlbum With the AlbumId
                onAlbumClicked = { albumId ->
                    navController.navigate(Screens.ViewAlbum(albumId))
                }
            )
        }

        composable<Screens.ViewAlbum> { backStackEntry ->
            val viewAlbum: Screens.ViewAlbum = backStackEntry.toRoute()
            val viewModel = hiltViewModel<ViewAlbumViewModel>() // Initiate the ViewModel
            viewModel.getAlbumById(albumId = viewAlbum.albumId) // Get the album call in the process
            ViewAlbumScreen(
                viewModel = viewModel,
                onDeleteFabClicked = {albumId ->

                },
                onEditFabClicked = {albumID ->
                    navController.navigate(Screens.AddOrEditAlbum(albumId = albumID))
                },
            )
        }
    }
}