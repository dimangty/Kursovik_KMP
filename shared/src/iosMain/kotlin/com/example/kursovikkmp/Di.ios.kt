@file:OptIn(BetaInteropApi::class)

package com.example.kursovikkmp

import com.example.kursovikkmp.feature.news.list.NewsListViewModel
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.ObjCProtocol
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import kotlin.reflect.KClass

internal actual val platformModule: Module = module {
    factoryOf(::NewsListViewModel)
}

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
