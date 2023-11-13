package com.example.kmptest

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.CoinDataDatabase

class AndroidPlatformLocal : PlatformLocalIO {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"

    override fun readFile(path: String, filename: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun saveFile(path: String, filename: String, nbLine: Int) {
        TODO("Not yet implemented")
    }
}

actual fun getPlatformIO(): PlatformLocalIO = AndroidPlatformLocal()

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(CoinDataDatabase.Schema, context, "coindatadatabase.db")
    }
}
