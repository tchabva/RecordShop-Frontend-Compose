package uk.udemy.recordshop.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import uk.udemy.recordshop.ui.artists.ArtistsScreen
import uk.udemy.recordshop.ui.genres.GenresScreen

@Composable
fun NavigationGraph(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: Any = Home,
    innerPadding: PaddingValues
) = NavHost(
    modifier = modifier,
    navController = navController,
    startDestination = startDestination

){

    homeGraph(innerPadding)
    composable<Artists>{ ArtistsScreen(innerPadding) }
    composable<Genres>{ GenresScreen(innerPadding) }
}