package com.example.kmptest.store.coindata.sot

import com.example.CoinData
import com.example.CoinDataDatabase
import com.example.kmptest.store.coindata.CoinDataKey
import kotlinx.coroutines.flow.flow
import org.mobilenativefoundation.store.store5.SourceOfTruth

class CoinCapSourceOfTruthProvider(private val db: CoinDataDatabase) {
    fun provide(): SourceOfTruth<CoinDataKey, CoinData, CoinData> = SourceOfTruth.of(
        reader = { key: CoinDataKey ->
            flow<CoinData> {
                //require(key is CoinDataKey.Read)
                println("BEFORE READ ==== $key")
                when (key) {
                    is CoinDataKey.Read.AllAgencies -> {
                        val cpbVersionData = db.coinDataDatabaseQueries.selectAllCoinDataInfo()
                            .executeAsOneOrNull()
                        if (cpbVersionData != null)
                            emit(cpbVersionData)
                    }

                    is CoinDataKey.Read.ByCoinSymbol -> {
                        val cpbVersionData =
                            db.coinDataDatabaseQueries.getCoinDataBySymbol(key.coinSymbol)
                                .executeAsOne()
                        println("cpbVersionData :: $cpbVersionData")
//                        if (cpbVersionData != null)
                            emit(cpbVersionData)
                    }
                    else -> Unit
                }
            }
        },
        writer = { key: CoinDataKey, coinData: CoinData ->
            //require(key is CoinDataKey.Write)
            println("BEFORE WRITE ==== $key")
            db.coinDataDatabaseQueries.upsert(coinData)
        },
        delete = { key: CoinDataKey ->
            //require(key is CoinDataKey.Clear)
            when (key) {
                is CoinDataKey.Clear.All -> db.coinDataDatabaseQueries.removeAllCoinData()
                is CoinDataKey.Clear.ByCoinSymbol -> Unit
                else -> Unit
            }
        },
        //deleteAll = db.postDao()::clearAllFeeds
    )
}
