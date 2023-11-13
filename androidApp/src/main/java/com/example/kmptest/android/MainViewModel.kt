package com.example.kmptest.android

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmptest.CoinCapStore
import com.example.kmptest.DriverFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val coinCapStore = CoinCapStore(DriverFactory(this.getApplication()))

    private val _coinPrice = MutableStateFlow("")
    val coinPrice: StateFlow<String> = _coinPrice

    fun fetchCoinData() {
        viewModelScope.launch {
            println("getting x ::")
            val x = coinCapStore.store.getCoinDataBySymbol("btc")
            _coinPrice.value = x.priceUsd
            println("x :: $x returned")
        }
    }
}
