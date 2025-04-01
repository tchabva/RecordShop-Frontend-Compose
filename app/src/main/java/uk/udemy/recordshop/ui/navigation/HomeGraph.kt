package uk.udemy.recordshop.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uk.udemy.recordshop.ui.addOrEditAlbum.AddOrEditAlbumScreen
import uk.udemy.recordshop.ui.addOrEditAlbum.AddOrEditAlbumViewModel
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
                // Navigates to the View Album Screen With the AlbumId
                onAlbumItemClick = { albumId ->
                    navController.navigate(Screens.ViewAlbum(albumId))
                }
            )
        }

        composable<Screens.ViewAlbum> { backStackEntry ->
            val viewAlbum: Screens.ViewAlbum = backStackEntry.toRoute()
            val viewModel = hiltViewModel<ViewAlbumViewModel>() // Initiate the ViewModel
            LaunchedEffect(viewAlbum.albumId) {
                viewModel.getAlbumById(albumId = viewAlbum.albumId)
            }
            // Get the album call in the process
            ViewAlbumScreen(
                viewModel = viewModel,
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

        // For navigating to the Add Album Screen from the HomeTab
        composable<Screens.AddOrEditAlbum> { backStackEntry ->
            val editAlbum: Screens.AddOrEditAlbum = backStackEntry.toRoute()
            val viewModel = hiltViewModel<AddOrEditAlbumViewModel>()
            if (editAlbum.albumId != null) {
                /*
                If albumId isn't null use Coroutine to invoke getAlbumById method in the ViewModel
                and then proceed to the Edit Album State. Otherwise Load into the Add Album State
                */
                LaunchedEffect(editAlbum.albumId) {
                    viewModel.getAlbumById(editAlbum.albumId)
                }
            }
            AddOrEditAlbumScreen(
                viewModel = viewModel,
                navigateToHomeGraph = {
                    /*
                    Pop everything up to and including the Home Screen from the backstack and then
                    navigate to then navigate to the Home Screen
                     */
                    navController.navigate(Screens.Home) {
                        popUpTo(Screens.Home) { inclusive = true }
                    }
                    // Launches a snackbar informing the user that the album has been added
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Album Added Successfully")
                    }
                },
                albumSuccessfullyUpdated = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Album Updated Successfully")
                    }
                }
            )
        }
    }
}