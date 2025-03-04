package com.example.kursovikkmp.android

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.kursovikkmp.android.feature.Favorites.FavoriteDetailsScreen
import com.example.kursovikkmp.android.feature.Favorites.FavoriteScreen
import com.example.kursovikkmp.android.feature.News.NewsDetailsScreen
import com.example.kursovikkmp.android.feature.News.NewsScreen
import com.example.kursovikkmp.navigation.NavigationAction
import com.example.kursovikkmp.navigation.NavigationService
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject

@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var selectedItem by remember { mutableIntStateOf(0) }
    val navigationService: NavigationService by inject(NavigationService::class.java)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navigationService.setNavController(navController)
                BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->
                    NavigationBarItem(
                        selected = index == selectedItem,
                        label = {
                            Text(navigationItem.label)
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.label
                            )
                        },
                        onClick = {
                            selectedItem = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(paddingValues = paddingValues)) {
            composable(Screens.Home.route) {
                NewsScreen()
            }
            composable(Screens.Favorites.route) {
                FavoriteScreen()
            }

            composable<NavigationAction.NavigateToNewsDetails> {
                val args = it.toRoute<NavigationAction.NavigateToNewsDetails>()
                NewsDetailsScreen(
                    args.title
                )
            }

            composable<NavigationAction.NavigateToFavoritesDetails> {
                val args = it.toRoute<NavigationAction.NavigateToFavoritesDetails>()
                FavoriteDetailsScreen(
                    args.title
                )
            }
        }
    }
}