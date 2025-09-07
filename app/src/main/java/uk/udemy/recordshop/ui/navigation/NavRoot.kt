package uk.udemy.recordshop.ui.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uk.udemy.recordshop.ui.navigation.navgraphs.NavigationGraph

@Composable
fun NavRoot() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Shared state for artist name
    var artistName by remember { mutableStateOf<String?>(null) }

    // Reset artist name when navigating away from Artist screen
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    if (currentDestination?.hasRoute(Screens.Artist::class) != true) {
        artistName = null
    }

    Scaffold(
        topBar = {
            TopBar(navController = navController, artistName = artistName)
        },

        bottomBar = { BottomNav(navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        NavigationGraph(
            modifier = Modifier,
            navController = navController,
            innerPadding = innerPadding,
            snackbarHostState = snackbarHostState,
            coroutineScope = scope,
            onArtistNameChanged = { name -> artistName = name }
        )
    }
}