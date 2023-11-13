package com.example.kmptest.store.coindata.bookkeeper

import com.example.kmptest.store.coindata.CoinDataKey
import org.mobilenativefoundation.store.store5.Bookkeeper

class CoinCapBookkeeperProvider {
    fun provide(): Bookkeeper<CoinDataKey> = Bookkeeper.by(
        getLastFailedSync = { 1L },
        setLastFailedSync = { _, _ -> true },
        clear = { true },
        clearAll = { true }
    )
}