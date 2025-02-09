package uk.udemy.recordshop.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import uk.udemy.recordshop.ui.home.HomeScreen
import uk.udemy.recordshop.ui.home.HomeViewModel

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation<Tabs.Home>(startDestination = Screens.Home) {
        composable<Screens.Home>() {
            HomeScreen(
                onAddAlbumClick = {
                    navController.navigate(Screens.AddOrEditAlbum())
                },
                viewModel = hiltViewModel<HomeViewModel>()
            )
        }
    }
}