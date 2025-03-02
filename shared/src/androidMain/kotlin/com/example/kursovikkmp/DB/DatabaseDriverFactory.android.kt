package com.example.kursovikkmp.DB

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.kursovikkmp.Database

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun create(): SqlDriver = AndroidSqliteDriver(schema = Database.Schema.synchronous(), context = context, name = "KursoviiKMP.db")
}