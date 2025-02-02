package com.example.data.local

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

expect fun buildDatabaseDriveFactory(): DatabaseDriverFactory