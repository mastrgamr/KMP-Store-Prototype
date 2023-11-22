package com.example.kmptest.store.coindata.fetcher

import com.example.CoinData
import com.example.kmptest.CoinCapApi
import com.example.kmptest.RequestResult
import com.example.kmptest.store.coindata.CoinDataKey
import org.mobilenativefoundation.store.store5.Fetcher

class CoinCapFetcherProvider(private val api: CoinCapApi) {
    fun provide(): Fetcher<CoinDataKey, RequestResult<CoinData>> = Fetcher.of { key ->
        println("BEFORE FETCH ==== $key")
        require(key is CoinDataKey.Read)
        when (key) {
            is CoinDataKey.Read.AllAgencies -> RequestResult.Exception(Throwable("Not Implemented"))
            is CoinDataKey.Read.ByCoinSymbol -> when (val requestResult = api.getCoinCapData()) {
                is RequestResult.Exception -> RequestResult.Exception(Throwable("Not Implemented"))
                is RequestResult.Success -> {
                    val data = requestResult.data.coinDataList.first()
                    println("FETCH SUCCESS ==== $data")
                    RequestResult.Success(
                        CoinData(
                            id = data.id,
                            symbol = data.symbol,
                            name = data.name,
                            priceUsd = data.priceUsd,
                            changePercent24Hr = data.changePercent24Hr,
                        )
                    )
                }
            }
        }
    }
}