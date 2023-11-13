package com.example.kmptest.store.coindata

import com.example.CoinData
import com.example.CoinDataDatabase
import com.example.kmptest.CoinCapApi
import com.example.kmptest.store.coindata.bookkeeper.CoinCapBookkeeperProvider
import com.example.kmptest.store.coindata.converter.CoinCapConverterProvider
import com.example.kmptest.store.coindata.fetcher.CoinCapFetcherProvider
import com.example.kmptest.store.coindata.sot.CoinCapSourceOfTruthProvider
import com.example.kmptest.store.coindata.updater.CoinCapUpdaterProvider
import org.mobilenativefoundation.store.store5.ExperimentalStoreApi
import org.mobilenativefoundation.store.store5.MemoryPolicy
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.MutableStoreBuilder
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalStoreApi::class)
class CoinCapStoreProvider(
    private val api: CoinCapApi,
    private val database: CoinDataDatabase
) {
    fun provide(): MutableStore<CoinDataKey, CoinData> =
        MutableStoreBuilder.from(
            fetcher = CoinCapFetcherProvider(api).provide(),
            sourceOfTruth = CoinCapSourceOfTruthProvider(database).provide(),
            converter = CoinCapConverterProvider().provide()
        )
        .cachePolicy(
            MemoryPolicy.builder<Any, Any>()
                .setMaxSize(10)
                .setExpireAfterWrite(3.minutes) // or setExpireAfterAccess(10.minutes)
                .build()
        )
        .build(
            updater = CoinCapUpdaterProvider(api).provide(),
            bookkeeper = CoinCapBookkeeperProvider().provide()
        )
}
