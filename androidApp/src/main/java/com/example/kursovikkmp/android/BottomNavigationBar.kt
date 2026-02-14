package com.example.kursovikkmp.android

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.kursovikkmp.feature.home.HomeViewModel
import com.example.kursovikkmp.navigation.NavigationAction
import com.example.kursovikkmp.navigation.NavigationServiceImpl
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent.inject

private data class BottomNavigationUiItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BottomNavigationBar() {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = koinViewModel()
    val homeState by homeViewModel.flowState.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val navigationService: NavigationServiceImpl by inject(NavigationServiceImpl::class.java)
    val defaultTabs = listOf("News", "Favorites", "Рецепты", "Холодильник")
    val tabs = if (homeState.tabs.size >= 4) homeState.tabs else defaultTabs
    val bottomNavigationItems = listOf(
        BottomNavigationUiItem(
            label = tabs[0],
            icon = Icons.Filled.Home,
            route = Screens.Home.route
        ),
        BottomNavigationUiItem(
            label = tabs[1],
            icon = Icons.Filled.Favorite,
            route = Screens.Favorites.route
        ),
        BottomNavigationUiItem(
            label = tabs[2],
            icon = Icons.Filled.Home,
            route = Screens.Recipes.route
        ),
        BottomNavigationUiItem(
            label = tabs[3],
            icon = Icons.Filled.List,
            route = Screens.Fridge.route
        )
    )
    val showMainBars = currentRoute == Screens.Home.route ||
            currentRoute == Screens.Favorites.route ||
            currentRoute == Screens.Recipes.route ||
            currentRoute == Screens.Fridge.route
    val topBarTitle = when (currentRoute) {
        Screens.Home.route -> tabs[0]
        Screens.Favorites.route -> tabs[1]
        Screens.Recipes.route -> tabs[2]
        Screens.Fridge.route -> tabs[3]
        else -> ""
    }

    navigationService.setNavController(navController)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showMainBars) {
                TopAppBar(
                    title = { Text(topBarTitle) },
                    actions = {
                        IconButton(
                            onClick = {
                                navController.navigate(Screens.Profile.route)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "Profile"
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (showMainBars) {
                NavigationBar {
                    bottomNavigationItems.forEach { navigationItem ->
                        NavigationBarItem(
                            selected = currentRoute == navigationItem.route,
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
            modifier = Modifier.padding(paddingValues)
        ) {

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

            composable(Screens.Recipes.route) {
                RecipesScreen()
            }

            composable(Screens.Fridge.route) {
                FridgeScreen()
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

            composable<NavigationAction.NavigateToRecipesDetails> {
                val args = it.toRoute<NavigationAction.NavigateToRecipesDetails>()
                RecipesDetailsScreen(
                    args.recipeId
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
