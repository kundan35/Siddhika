package com.siddhika.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.siddhika.core.util.Constants
import com.siddhika.data.local.database.SiddhikaDatabase

class DatabaseDriverFactory {
    fun create(): SqlDriver {
        return NativeSqliteDriver(
            schema = SiddhikaDatabase.Schema,
            name = Constants.DATABASE_NAME
        )
    }
}
