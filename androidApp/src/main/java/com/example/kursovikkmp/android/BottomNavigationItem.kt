package com.example.kursovikkmp.android

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = "",
    val screens: List<String> = listOf()
) {
    fun isSelected(destination : String?) : Boolean {
        if (screens.contains(destination)) {
            return true
        }

        if (destination == route) {
            return true
        }

        return false
    }

    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "News",
                icon = Icons.Filled.Home,
                route = Screens.Home.route,
                screens = listOf(Screens.Home.route, Screens.Details.route)
            ),
            BottomNavigationItem(
                label = "Favorites",
                icon = Icons.Filled.Favorite,
                route = Screens.Favorites.route
            )
        )
    }
}