package com.example.kursovikkmp

import com.example.kursovikkmp.DI.NetworkModule
import com.example.kursovikkmp.DI.StorageModule
import com.example.kursovikkmp.common.mvvm.LceStateManager
import com.example.kursovikkmp.feature.news.NewsService
import com.example.kursovikkmp.navigation.NavigationService
import com.example.kursovikkmp.network.NetworkSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

const val IO_DISPATCHER_NAME = "ioDispatcher"
const val MAIN_DISPATCHER_NAME = "mainDispatcher"
const val DEFAULT_DISPATCHER_NAME = "defaultDispatcher"
const val DEFAULT_SCOPE = "defaultScope"

val sharedModule: Module
    get() = module {
        includes(platformModule + commonModule + NetworkCompositeModule + StorageCompositeModule + vmModule)
    }

internal val commonModule = module {
    factory { Dispatchers.Default + SupervisorJob() }
    singleOf(::NewsService)
    factoryOf(::LceStateManager)
    singleOf(::NetworkSettings)
}

internal val NetworkCompositeModule: Module = module {
    includes(NetworkModule.json, NetworkModule.httpClient, NetworkModule.api)
}

internal val StorageCompositeModule: Module = module {
    includes(StorageModule.dbModule, StorageModule.daoModule, StorageModule.repositoryModule)
}


internal expect val platformModule: Module
internal expect val vmModule: Module