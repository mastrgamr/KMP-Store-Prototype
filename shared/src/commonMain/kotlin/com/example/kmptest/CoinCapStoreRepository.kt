package com.example.kmptest

import com.example.CoinData
import com.example.kmptest.store.coindata.CoinDataKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.mobilenativefoundation.store.store5.ExperimentalStoreApi
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import org.mobilenativefoundation.store.store5.StoreWriteRequest
import org.mobilenativefoundation.store.store5.StoreWriteResponse

private const val TAG = "CoinCapStoreRepository"

interface CoinCapStoreRepository {
    suspend fun getCoinDataBySymbol(coinSymbol: String): CoinData
    fun stream(request: StoreReadRequest<CoinDataKey>): Flow<StoreReadResponse<CoinData>>
    suspend fun write(request: StoreWriteRequest<CoinDataKey, CoinData, CoinData>): StoreWriteResponse
}

@OptIn(ExperimentalStoreApi::class)
class CoinCapStoreRepositoryImpl(private val store: MutableStore<CoinDataKey, CoinData>) : CoinCapStoreRepository {
    override suspend fun getCoinDataBySymbol(coinSymbol: String): CoinData {
        println("$TAG: COINCAP STORE ==== $store")
        val first = store.stream<CoinData>(StoreReadRequest.cached(CoinDataKey.Read.ByCoinSymbol(coinSymbol), refresh = false))
            .first { storeReadResponse ->
                println("$TAG: STORE RESPONSE ==== $storeReadResponse")
                storeReadResponse.dataOrNull()?.symbol.equals(coinSymbol, true)
            }.requireData()
        println("$TAG: first :: $first") // null?!!

        return first
    }

    override fun stream(request: StoreReadRequest<CoinDataKey>): Flow<StoreReadResponse<CoinData>> {
        println("$TAG: COINCAP STORE ==== $store")
        return store.stream<CoinData>(request)
    }

    override suspend fun write(request: StoreWriteRequest<CoinDataKey, CoinData, CoinData>): StoreWriteResponse = store.write(request)
}
