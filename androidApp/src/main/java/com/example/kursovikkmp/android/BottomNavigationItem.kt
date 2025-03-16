package com.example.kursovikkmp.android

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kursovikkmp.feature.home.HomeViewModel
import com.example.kursovikkmp.navigation.NavigationService
import org.koin.mp.KoinPlatform.getKoin

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = "",
    val screens: List<String> = listOf()
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        val homeViewModel by getKoin().inject<HomeViewModel>()
        val tabs = homeViewModel.state.tabs
        return listOf(
            BottomNavigationItem(
                label = tabs[0],
                icon = Icons.Filled.Home,
                route = Screens.Home.route,
                screens = listOf(Screens.Home.route, Screens.Details.route)
            ),
            BottomNavigationItem(
                label = tabs[1],
                icon = Icons.Filled.Favorite,
                route = Screens.Favorites.route
            )
        )
    }
}