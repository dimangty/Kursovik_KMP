package com.example.kursovikkmp.android

sealed class Screens(val route : String) {
    object Login : Screens("login_screen")
    object SignUp : Screens("signup_screen")
    object Pin : Screens("pin_screen")
    object Main : Screens("main_screen")
    object Home : Screens("home_screen")
    object Search : Screens("search_screen")
    object Profile : Screens("profile_screen")
    object Details : Screens("details_screen")
    object Favorites : Screens("favorites_screen")
    object Recipes : Screens("recipes_screen")
    object Fridge : Screens("fridge_screen")
}
