package com.example.kursovikkmp.navigation

import kotlinx.coroutines.flow.StateFlow

expect class NavigationService {
    val currentDestination: StateFlow<String>
    fun navigate(action: NavigationAction)
    fun navigateBack()
    fun <T> setPreviousBackStackEntry(key: String, value: T)
    fun <T> getCurrentBackStackEntry(key: String): T?
    fun <T> clearCurrentBackStackEntry(key: String)
}