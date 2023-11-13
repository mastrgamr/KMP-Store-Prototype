package com.example.kmptest

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.CoinDataDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile
import platform.Foundation.writeToFile
import platform.UIKit.UIDevice

class IOSPlatformLocal: PlatformLocalIO {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    @OptIn(ExperimentalForeignApi::class)
    override fun readFile(path : String, filename: String) : Boolean {
        val string = NSString.stringWithContentsOfFile(path, NSUTF8StringEncoding, null) ?: return false
        string.lines().forEach {
            println(it)
        }
        return true
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun saveFile(path : String, filename : String, nbLine : Int) {
        val result = (1..nbLine).map {
            "string $it"
        }
        (result as NSString).writeToFile(path, true, NSUTF8StringEncoding, null)
    }
}

actual fun getPlatformIO(): PlatformLocalIO = IOSPlatformLocal()

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(CoinDataDatabase.Schema, "versions.db")
    }
}