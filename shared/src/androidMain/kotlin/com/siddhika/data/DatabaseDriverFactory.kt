package com.siddhika.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.siddhika.core.util.Constants
import com.siddhika.data.local.database.SiddhikaDatabase

class DatabaseDriverFactory(private val context: Context) {
    fun create(): SqlDriver {
        return AndroidSqliteDriver(
            schema = SiddhikaDatabase.Schema,
            context = context,
            name = Constants.DATABASE_NAME
        )
    }
}
