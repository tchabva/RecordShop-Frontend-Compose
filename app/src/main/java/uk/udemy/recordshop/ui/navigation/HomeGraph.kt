package uk.udemy.recordshop.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import uk.udemy.recordshop.ui.home.HomeScreen

fun NavGraphBuilder.homeGraph(
    paddingValues: PaddingValues
){
    composable<Home>() { HomeScreen(paddingValues) }
}