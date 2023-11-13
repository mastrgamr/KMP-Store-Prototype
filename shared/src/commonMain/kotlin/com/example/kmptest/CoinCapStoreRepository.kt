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

interface CoinCapStoreRepository {
    suspend fun getCoinDataBySymbol(coinSymbol: String): CoinData
    fun stream(request: StoreReadRequest<CoinDataKey>): Flow<StoreReadResponse<CoinData>>
    suspend fun write(request: StoreWriteRequest<CoinDataKey, CoinData, CoinData>): StoreWriteResponse
}

@OptIn(ExperimentalStoreApi::class)
class CoinCapStoreRepositoryImpl(private val store: MutableStore<CoinDataKey, CoinData>) : CoinCapStoreRepository {
    override suspend fun getCoinDataBySymbol(coinSymbol: String): CoinData {
        println("COINCAP STORE ==== $store")
        val first = store.stream<CoinData>(StoreReadRequest.fresh(CoinDataKey.Read.ByCoinSymbol(coinSymbol))).first { storeReadResponse ->
            println("STORE RESPONSE ==== $storeReadResponse")
            storeReadResponse.dataOrNull()?.symbol == coinSymbol
        }.requireData()
        println("gottem :: $first") // null?!!
        return first
    }

    override fun stream(request: StoreReadRequest<CoinDataKey>): Flow<StoreReadResponse<CoinData>> {
        println("COINCAP STORE ==== $store")
        return store.stream<CoinData>(request)
    }

    override suspend fun write(request: StoreWriteRequest<CoinDataKey, CoinData, CoinData>): StoreWriteResponse = store.write(request)
}
