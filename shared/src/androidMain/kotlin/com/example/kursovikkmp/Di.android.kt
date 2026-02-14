package com.example.kursovikkmp

import com.example.kursovikkmp.DB.DatabaseDriverFactory
import com.example.kursovikkmp.feature.device.DeviceService
import com.example.kursovikkmp.feature.device.DeviceServiceImpl
import com.example.kursovikkmp.feature.device.ResourceService
import com.example.kursovikkmp.feature.device.ResourceServiceImpl
import com.example.kursovikkmp.feature.favorites.details.FavoriteDetailsViewModel
import com.example.kursovikkmp.feature.news.list.NewsListViewModel
import com.example.kursovikkmp.feature.favorites.list.FavoritesListViewModel
import com.example.kursovikkmp.feature.news.details.NewsDetailsViewModel
import com.example.kursovikkmp.feature.home.HomeViewModel
import com.example.kursovikkmp.feature.auth.login.LoginViewModel
import com.example.kursovikkmp.feature.auth.pin.PinViewModel
import com.example.kursovikkmp.feature.auth.signup.SignUpViewModel
import com.example.kursovikkmp.feature.fridge.list.FridgeViewModel
import com.example.kursovikkmp.feature.profile.ProfileViewModel
import com.example.kursovikkmp.feature.recipes.details.RecipesDetailsViewModel
import com.example.kursovikkmp.feature.recipes.list.RecipesListViewModel
import com.example.kursovikkmp.navigation.NavigationService
import com.example.kursovikkmp.navigation.NavigationServiceImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal actual val platformModule: Module = module {
    single<DatabaseDriverFactory> { DatabaseDriverFactory(get()) }
    single<NavigationService> { NavigationServiceImpl() }
    single<DeviceService> { DeviceServiceImpl(get()) }
    single<ResourceService> { ResourceServiceImpl(get()) }
    single { get<NavigationService>() as NavigationServiceImpl }
}

internal actual val vmModule: Module = module {
    viewModelOf(::NewsListViewModel)
    viewModelOf(::FavoritesListViewModel)
    viewModelOf(::NewsDetailsViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::FavoriteDetailsViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::PinViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::RecipesListViewModel)
    viewModelOf(::RecipesDetailsViewModel)
    viewModelOf(::FridgeViewModel)
}
