package com.example.kmptest

import com.example.CoinDataDatabase
import com.example.kmptest.store.coindata.CoinCapStoreProvider
import org.mobilenativefoundation.store.store5.ExperimentalStoreApi

@OptIn(ExperimentalStoreApi::class)
class CoinCapStore(databaseDriverFactory: DriverFactory) {
    private val platformIO: PlatformLocalIO = getPlatformIO()

    private val api = CoinCapApi()
    private val driver = databaseDriverFactory.createDriver()
    private val db = CoinDataDatabase(driver)

    private val _store = CoinCapStoreProvider(api, db).provide()
    val store = CoinCapStoreRepositoryImpl(_store)

    fun sayHello(): String {
        return "Hello, ${platformIO.name}!"
    }
}
