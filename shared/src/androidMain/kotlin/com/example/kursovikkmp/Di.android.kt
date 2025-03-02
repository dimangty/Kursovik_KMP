package com.example.kursovikkmp

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.kursovikkmp.DB.DatabaseDriverFactory

import com.example.kursovikkmp.feature.news.list.NewsListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal actual val platformModule: Module = module {
    single<DatabaseDriverFactory> { DatabaseDriverFactory(get()) }
}
internal actual val vmModule: Module = module {
    viewModelOf(::NewsListViewModel)
}