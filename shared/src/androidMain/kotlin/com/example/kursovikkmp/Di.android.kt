package com.example.kursovikkmp

import com.example.kursovikkmp.feature.news.list.NewsListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal actual val platformModule: Module = module {
    factoryOf(::NewsListViewModel)
}
