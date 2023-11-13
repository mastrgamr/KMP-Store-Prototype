package com.example.kmptest

import com.example.CoinDataDatabase

class CoinCapDatabaseProvider {
    suspend fun provide(driverFactory: DriverFactory): CoinDataDatabase = CoinDataDatabase(driverFactory.createDriver())
}