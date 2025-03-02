package com.example.kursovikkmp.DB

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.kursovikkmp.Database

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver = NativeSqliteDriver(Database.Schema.synchronous(), "database.db")
}