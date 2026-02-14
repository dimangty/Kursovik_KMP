package com.example.kursovikkmp.navigation

import android.annotation.SuppressLint
import android.os.Looper
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavigationServiceImpl : NavigationService {
    private companion object {
        const val LOGIN_ROUTE = "login_screen"
        const val SIGN_UP_ROUTE = "signup_screen"
        const val PIN_ROUTE = "pin_screen"
        const val HOME_ROUTE = "home_screen"
    }

    @SuppressLint("StaticFieldLeak")
    private var navController: NavHostController? = null

    private val _currentDestination = MutableStateFlow("")
    override val currentDestination = _currentDestination.asStateFlow()

    private val onDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            _currentDestination.update {
                destination.route?.substringBefore("/") ?: ""
            }
        }

    fun setNavController(navController: NavHostController) {
        this.navController = navController
        navController.removeOnDestinationChangedListener(onDestinationChangedListener)
        navController.addOnDestinationChangedListener(onDestinationChangedListener)
    }

    override fun <T> setPreviousBackStackEntry(key: String, value: T) {
        android.os.Handler(Looper.getMainLooper()).post {
            navController?.previousBackStackEntry
                ?.savedStateHandle
                ?.set(key, value)
        }
    }

    override fun <T> getCurrentBackStackEntry(key: String): T? {
        val current = navController?.currentBackStackEntry
        return if (current != null && current.savedStateHandle.contains(key)) {
            current.savedStateHandle[key]
        } else {
            null
        }
    }

    override fun <T> clearCurrentBackStackEntry(key: String) {
        android.os.Handler(Looper.getMainLooper()).post {
            navController?.currentBackStackEntry?.savedStateHandle?.remove<T>(key)
        }
    }

    @Suppress("LongMethod", "CyclomaticComplexMethod") // TODO
    override fun navigate(action: NavigationAction) {
        android.os.Handler(Looper.getMainLooper()).post {
            navController?.run {
                when (action) {
                    is NavigationAction.NavigateToFavoritesDetails,
                    is NavigationAction.NavigateToNewsDetails,
                    is NavigationAction.NavigateToRecipesDetails -> {
                        navigate(action)
                    }

                    NavigationAction.NavigateToSignUp -> {
                        navigate(SIGN_UP_ROUTE)
                    }

                    NavigationAction.NavigateToPin -> {
                        navigate(PIN_ROUTE)
                    }

                    NavigationAction.NavigateToMain -> {
                        navigate(HOME_ROUTE) {
                            popUpTo(LOGIN_ROUTE) { inclusive = true }
                            launchSingleTop = true
                        }
                    }

                    NavigationAction.NavigateToLogin -> {
                        navigate(LOGIN_ROUTE) {
                            popUpTo(graph.findStartDestination().id) { inclusive = true }
                            launchSingleTop = true
                        }
                    }

                    NavigationAction.NavigateBack -> {
                        navigateBack()
                    }
                }
            }
        }
    }

    override fun navigateBack() {
        android.os.Handler(Looper.getMainLooper()).post {
            navController?.navigateUp()
        }
    }
}
