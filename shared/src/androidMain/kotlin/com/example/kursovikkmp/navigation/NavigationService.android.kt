package com.example.kursovikkmp.navigation

import android.annotation.SuppressLint
import android.os.Looper
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

actual class NavigationService {
    @SuppressLint("StaticFieldLeak")
    private var navController: NavHostController? = null

    private val _currentDestination = MutableStateFlow("")
    actual val currentDestination = _currentDestination.asStateFlow()

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

    actual fun <T> setPreviousBackStackEntry(key: String, value: T) {
        android.os.Handler(Looper.getMainLooper()).post {
            navController?.previousBackStackEntry
                ?.savedStateHandle
                ?.set(key, value)
        }
    }

    actual fun <T> getCurrentBackStackEntry(key: String): T? {
        val current = navController?.currentBackStackEntry
        return if (current != null && current.savedStateHandle.contains(key)) {
            current.savedStateHandle[key]
        } else {
            null
        }
    }

    actual fun <T> clearCurrentBackStackEntry(key: String) {
        android.os.Handler(Looper.getMainLooper()).post {
            navController?.currentBackStackEntry?.savedStateHandle?.remove<T>(key)
        }
    }

    @Suppress("LongMethod", "CyclomaticComplexMethod") // TODO
    actual fun navigate(action: NavigationAction) {
        android.os.Handler(Looper.getMainLooper()).post {
            navController?.run {
                when (action) {
                    is NavigationAction.NavigateToFavoritesDetails,
                    is NavigationAction.NavigateToNewsDetails,
                        -> {
                        navigate(action)
                    }

                    NavigationAction.NavigateBack -> {
                        navigateBack()
                    }
                }
            }
        }
    }

    actual fun navigateBack() {
        android.os.Handler(Looper.getMainLooper()).post {
            navController?.navigateUp()
        }
    }
}