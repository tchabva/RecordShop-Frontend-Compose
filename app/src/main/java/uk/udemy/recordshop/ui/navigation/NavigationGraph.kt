package uk.udemy.recordshop.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import uk.udemy.recordshop.ui.navigation.navgraphs.artistGraph
import uk.udemy.recordshop.ui.navigation.navgraphs.genresGraph
import uk.udemy.recordshop.ui.navigation.navgraphs.homeGraph

// The Root NavGraph
@Composable
fun NavigationGraph(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: Any = Tabs.Home,
    innerPadding: PaddingValues,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) = NavHost(
    modifier = modifier.padding(innerPadding),
    navController = navController,
    startDestination = startDestination

) {

    // The nested Home Tab NavGraph
    homeGraph(
        navController = navController,
        snackbarHostState = snackbarHostState,
        coroutineScope = coroutineScope
    )

    // The nested Artists Tab NavGraph
    artistGraph(
        navController = navController
    )

    // The nested Genres Tab NavGraph
    genresGraph(
        navController = navController
    )
}