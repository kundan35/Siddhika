package com.siddhika.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.siddhika.data.local.database.SiddhikaDatabase
import java.io.File

class DatabaseDriverFactory {
    fun create(): SqlDriver {
        val databasePath = File(System.getProperty("user.home"), ".siddhika")
        databasePath.mkdirs()
        val databaseFile = File(databasePath, "siddhika.db")

        val driver = JdbcSqliteDriver("jdbc:sqlite:${databaseFile.absolutePath}")

        if (!databaseFile.exists()) {
            SiddhikaDatabase.Schema.create(driver)
        }

        return driver
    }
}
