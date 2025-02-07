package uk.udemy.recordshop.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uk.udemy.recordshop.ui.artists.ArtistsScreen
import uk.udemy.recordshop.ui.genres.GenresScreen
import uk.udemy.recordshop.ui.home.HomeScreen

@Composable
fun NavRoot(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            NavigationBar {
                topLevelRoute.forEach{topLevelRoute ->
                    val isSelected =
                        currentDestination?.hierarchy?.any {
                            it.hasRoute(topLevelRoute.route::class)
                        } == true
                    NavigationBarItem(

                        icon = { Icon(topLevelRoute.icon, contentDescription = topLevelRoute.name) },
                        label = { Text(topLevelRoute.name) },
                        selected = isSelected,
                        onClick = {
                            navController.navigate(topLevelRoute.route){
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Home,
            ){
            composable<Home>{ HomeScreen(innerPadding) }
            composable<Artists>{ ArtistsScreen(innerPadding) }
            composable<Genres>{ GenresScreen(innerPadding) }
        }
    }
}