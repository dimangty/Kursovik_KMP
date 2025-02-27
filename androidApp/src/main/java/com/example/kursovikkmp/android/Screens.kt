package com.example.kursovikkmp.android

sealed class Screens(val route : String) {
    object Home : Screens("home_screen")
    object Search : Screens("search_screen")
    object Profile : Screens("profile_screen")
    object Details : Screens("details_screen")
}