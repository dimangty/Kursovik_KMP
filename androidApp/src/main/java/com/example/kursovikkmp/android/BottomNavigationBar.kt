package com.example.kursovikkmp.android

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
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
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.feature_favorites.FavoriteDetailsScreen
import com.example.feature_favorites.FavoriteScreen
import com.example.feature_news.NewsDetailsScreen
import com.example.feature_news.NewsScreen
import com.example.feature_auth.PinScreen
import com.example.kursovikkmp.navigation.NavigationAction
import com.example.kursovikkmp.navigation.NavigationService
import org.koin.java.KoinJavaComponent.inject

@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    var selectedItem by remember { mutableIntStateOf(0) }
    val navigationService: NavigationService by inject(NavigationService::class.java)

    navigationService.setNavController(navController)

    val showBottomBar = currentRoute == Screens.Home.route
            || currentRoute == Screens.Favorites.route
            || currentRoute == Screens.Profile.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
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
        }
    ) {paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Login.route,
            modifier = Modifier.padding(bottom = if (showBottomBar) paddingValues.calculateBottomPadding() else 0.dp)) {

            composable(Screens.Login.route) {
                com.example.feature_auth.LoginScreen()
            }

            composable(Screens.SignUp.route) {
                com.example.feature_auth.SignUpScreen()
            }

            composable(Screens.Pin.route) {
                PinScreen()
            }

            composable(Screens.Main.route) {
                navController.navigate(Screens.Home.route) {
                    popUpTo(Screens.Login.route) { inclusive = true }
                }
            }

            composable(Screens.Home.route) {
                NewsScreen()
            }

            composable(Screens.Favorites.route) {
                FavoriteScreen()
            }

            composable(Screens.Profile.route) {
                ProfileScreen()
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

            composable<NavigationAction.NavigateToSignUp> {
                navController.navigate(Screens.SignUp.route)
            }

            composable<NavigationAction.NavigateToPin> {
                navController.navigate(Screens.Pin.route)
            }

            composable<NavigationAction.NavigateToMain> {
                navController.navigate(Screens.Home.route) {
                    popUpTo(Screens.Login.route) { inclusive = true }
                }
            }

            composable<NavigationAction.NavigateToLogin> {
                navController.navigate(Screens.Login.route) {
                    popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }
}
