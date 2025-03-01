package com.example.kursovikkmp

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

import com.example.kursovikkmp.feature.news.list.NewsListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal actual val platformModule: Module = module {
    viewModelOf(::NewsListViewModel)
    single<SqlDriver> { AndroidSqliteDriver(schema = Database.Schema.synchronous(),
                                            context = get(), name = "Database") }
}
