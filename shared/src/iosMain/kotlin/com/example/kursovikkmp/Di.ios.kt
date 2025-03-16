@file:OptIn(BetaInteropApi::class)

package com.example.kursovikkmp

import com.example.kursovikkmp.DB.DatabaseDriverFactory
import com.example.kursovikkmp.feature.device.DeviceService
import com.example.kursovikkmp.feature.device.ResourceService
import com.example.kursovikkmp.feature.news.list.NewsListViewModel
import com.example.kursovikkmp.feature.favorites.list.FavoritesListViewModel
import com.example.kursovikkmp.feature.news.details.NewsDetailsViewModel
import com.example.kursovikkmp.feature.favorites.details.FavoriteDetailsViewModel
import com.example.kursovikkmp.feature.home.HomeViewModel
import com.example.kursovikkmp.navigation.NavigationService
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.ObjCProtocol
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import kotlin.reflect.KClass

fun initKoin(): KoinApplication {
    return startKoin {
        modules(sharedModule)
    }
}

fun getOriginalKotlinClass(objCClass: ObjCClass): KClass<*>? =
    kotlinx.cinterop.getOriginalKotlinClass(objCClass)

fun Koin.get(objCClass: ObjCClass, parameter: Any?): Any {
    val kClazz = kotlinx.cinterop.getOriginalKotlinClass(objCClass)!!
    return get(kClazz) { parametersOf(parameter) }
}

fun Koin.get(objCClass: ObjCClass): Any {
    val kClazz = kotlinx.cinterop.getOriginalKotlinClass(objCClass)!!
    return get(kClazz)
}

fun Koin.get(objCProtocol: ObjCProtocol): Any {
    val kClazz = kotlinx.cinterop.getOriginalKotlinClass(objCProtocol)!!
    return get(kClazz)
}

internal actual val vmModule: Module = module {
    factoryOf(::FavoriteDetailsViewModel)
    factoryOf(::NewsDetailsViewModel)
    factoryOf(::NewsListViewModel)
    factoryOf(::FavoritesListViewModel)
    factoryOf(::HomeViewModel)
}

internal actual val platformModule: Module = module {
    singleOf(::DeviceService)
    singleOf(::NavigationService)
    singleOf(::ResourceService)
    single<DatabaseDriverFactory> { DatabaseDriverFactory() }
}