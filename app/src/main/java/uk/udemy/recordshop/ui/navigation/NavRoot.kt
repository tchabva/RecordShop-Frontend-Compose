package uk.udemy.recordshop.ui.navigation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavRoot() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            // Determines whether the topAppBar is shown based on the Destination
            val showTopAppBar = Screens.screensWithTopAppBar.any {
                currentDestination?.hasRoute(it) == true
            }

            // Determines what the what the title of the TopAppBar will be
            val title = when {
                currentDestination?.hasRoute(Screens.AddOrEditAlbum::class) == true -> {
                    val albumId = (navBackStackEntry?.arguments?.getLong("albumId"))
                    Log.i("NavRoot", albumId.toString())
                    if (albumId != 0L) "Edit Album" else "Add Album"
                }
                else -> ""
            }

            AnimatedVisibility(
                visible = showTopAppBar,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
            ){
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            fontWeight = FontWeight.SemiBold,
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                )
            }
        },

        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val hideBottomNav = Screens.screensWithoutBottomNav.any {
                currentDestination?.hasRoute(it) == true
            }

            AnimatedVisibility(
                visible = !hideBottomNav,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                NavigationBar(
                    modifier = Modifier.height(92.dp),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    topLevelRoute.forEach { topLevelRoute ->
                        val isSelected =
                            currentDestination?.hierarchy?.any {
                                it.hasRoute(topLevelRoute.route::class)
                            } == true
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = topLevelRoute.icon(isSelected),
                                    contentDescription = topLevelRoute.name
                                )
                            },
                            label = { Text(topLevelRoute.name) },
                            selected = isSelected,
                            onClick = {
                                navController.navigate(topLevelRoute.route) {
                                    Log.i("NavRootNav", topLevelRoute.route.toString())
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // re-selecting the same item
                                    launchSingleTop = true
                                    // Restore state when re-selecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->

        NavigationGraph(
            modifier = Modifier,
            navController = navController,
            innerPadding = innerPadding,
            snackbarHostState = snackbarHostState,
            coroutineScope = scope
        )
    }
}