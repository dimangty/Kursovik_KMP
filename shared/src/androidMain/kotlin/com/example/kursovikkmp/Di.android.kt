package com.example.kursovikkmp

import com.example.kursovikkmp.DB.DatabaseDriverFactory
import com.example.kursovikkmp.feature.device.DeviceService
import com.example.kursovikkmp.feature.device.ResourceService
import com.example.kursovikkmp.feature.favorites.details.FavoriteDetailsViewModel

import com.example.kursovikkmp.feature.news.list.NewsListViewModel
import com.example.kursovikkmp.feature.favorites.list.FavoritesListViewModel
import com.example.kursovikkmp.feature.news.details.NewsDetailsViewModel
import com.example.kursovikkmp.feature.home.HomeViewModel
import com.example.kursovikkmp.navigation.NavigationService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal actual val platformModule: Module = module {
    single<DatabaseDriverFactory> { DatabaseDriverFactory(get()) }
    singleOf(::NavigationService)
    singleOf(::DeviceService)
    singleOf(::ResourceService)
}

internal actual val vmModule: Module = module {
    viewModelOf(::NewsListViewModel)
    viewModelOf(::FavoritesListViewModel)
    viewModelOf(::NewsDetailsViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::FavoriteDetailsViewModel)
}