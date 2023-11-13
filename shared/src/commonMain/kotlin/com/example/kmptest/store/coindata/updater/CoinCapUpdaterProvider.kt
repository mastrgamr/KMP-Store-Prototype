package com.example.kmptest.store.coindata.updater

import com.example.CoinData
import com.example.kmptest.CoinCapApi
import com.example.kmptest.store.coindata.CoinDataKey
import org.mobilenativefoundation.store.store5.Updater
import org.mobilenativefoundation.store.store5.UpdaterResult

class CoinCapUpdaterProvider(private val api: CoinCapApi) {
    fun provide(): Updater<CoinDataKey, CoinData, CoinData> = Updater.by(
        post = { key: CoinDataKey, _: CoinData ->
            require(key is CoinDataKey.Write)
            println("BEFORE UPDATE ==== $key")
            when (key) {
                is CoinDataKey.Write.Create -> TODO("Not Implemented")
                is CoinDataKey.Write.ByCoinSymbol -> {
                    try {
                        //require(value is StoreOutput.Data.Single)
                        val updaterResult = api.getCoinCapData()
                        UpdaterResult.Success.Typed(updaterResult)
                    } catch (error: Throwable) {
                        UpdaterResult.Error.Exception(error)
                    }
                }
            }
        }
    )
}
