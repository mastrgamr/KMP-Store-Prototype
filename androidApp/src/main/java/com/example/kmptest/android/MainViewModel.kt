package com.example.kmptest.android

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmptest.CoinCapStore
import com.example.kmptest.DriverFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"
class MainViewModel : ViewModel() {

    private lateinit var coinCapStore: CoinCapStore

    private val _coinPrice = MutableStateFlow("")
    val coinPrice: StateFlow<String> = _coinPrice

    fun fetchCoinData(context: Context) { // TODO: inject Context via Koin?
        viewModelScope.launch {
            coinCapStore = CoinCapStore(DriverFactory(context))
            val x = coinCapStore.store.getCoinDataBySymbol("BTC")
            _coinPrice.value = x.priceUsd
        }
    }
}
