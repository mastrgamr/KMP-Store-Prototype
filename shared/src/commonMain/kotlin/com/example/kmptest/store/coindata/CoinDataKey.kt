package com.example.kmptest.store.coindata

sealed class CoinDataKey {
    sealed class Read : CoinDataKey() {
        data object AllAgencies : Read()
        data class ByCoinSymbol(val coinSymbol: String) : Read()
    }

    sealed class Write : CoinDataKey() {
        data object Create : Write()
        data class ByCoinSymbol(val id: String, val symbol: String, val name: String, val priceUsd: String, val changePercent24Hr: String,) : Write()
    }

    sealed class Clear : CoinDataKey() {
        data object All : Clear()
        data class ByCoinSymbol(val coinSymbol: String) : Clear()
    }
}
