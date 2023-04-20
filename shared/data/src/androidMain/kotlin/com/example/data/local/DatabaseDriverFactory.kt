package com.example.data.local

import android.content.Context
import com.example.weather.cache.AppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, context, "test.db")
    }
}

actual fun buildDatabaseDriveFactory() = DatabaseDriverFactory(Manager.context!!)