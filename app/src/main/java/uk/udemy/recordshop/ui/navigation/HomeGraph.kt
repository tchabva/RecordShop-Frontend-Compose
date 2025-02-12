package uk.udemy.recordshop.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uk.udemy.recordshop.ui.home.HomeScreen
import uk.udemy.recordshop.ui.home.HomeViewModel
import uk.udemy.recordshop.ui.viewAlbum.ViewAlbumScreen
import uk.udemy.recordshop.ui.viewAlbum.ViewAlbumViewModel

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
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
                onDeleteAlbumConfirmed = { albumId ->


                    viewModel.deleteAlbum(albumId)
                    false
                },
                onEditFabClicked = { albumID ->
                    navController.navigate(Screens.AddOrEditAlbum(albumId = albumID))
                },
                onAlbumDeleted = {
                    /* 
                    Pop everything up to and including the Home Screen from the backstack and then 
                    navigate to then navigate to the Home Screen
                     */
                    navController.navigate(Screens.Home) {
                        popUpTo(Screens.Home) { inclusive = true }
                    }
                    // Launches a snackbar informing the user that the album has been deleted
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Album Deleted")
                    }
                }
            )
        }
    }
}