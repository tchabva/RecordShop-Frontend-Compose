package uk.udemy.recordshop.ui.navigation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun NavRoot() {
    val navController = rememberNavController()
    Scaffold(
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
                        Log.i(
                            "NavRoot",
                            currentDestination?.hierarchy?.joinToString { it.toString() } ?: "")
                        NavigationBarItem(

                            icon = {
                                Icon(
                                    topLevelRoute.icon,
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
        }
    ) { innerPadding ->

        NavigationGraph(
            modifier = Modifier,
            navController = navController,
            innerPadding = innerPadding
        )
    }
}