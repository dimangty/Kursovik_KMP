package com.example.kursovikkmp

import com.example.kursovikkmp.common.mvvm.LceStateManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val IO_DISPATCHER_NAME = "ioDispatcher"
const val MAIN_DISPATCHER_NAME = "mainDispatcher"
const val DEFAULT_DISPATCHER_NAME = "defaultDispatcher"

val sharedModule: Module
    get() = module {
        includes(commonModule)// + platformModule)
    }

internal val commonModule = module {
    single(named(IO_DISPATCHER_NAME)) { Dispatchers.IO }
    single(named(MAIN_DISPATCHER_NAME)) { Dispatchers.Main }
    single(named(DEFAULT_DISPATCHER_NAME)) { Dispatchers.Default }
    //factoryOf(::LceStateManager)
}

//internal expect val platformModule: Module
