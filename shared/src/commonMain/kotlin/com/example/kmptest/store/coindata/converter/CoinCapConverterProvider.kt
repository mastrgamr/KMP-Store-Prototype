package com.example.kmptest.store.coindata.converter

import com.example.CoinData
import com.example.kmptest.RequestResult
import io.ktor.util.converters.DataConversionException
import org.mobilenativefoundation.store.store5.Converter

class CoinCapConverterProvider {
    fun provide(): Converter<RequestResult<CoinData>, CoinData, CoinData> =
        Converter.Builder<RequestResult<CoinData>, CoinData, CoinData>()
            .fromNetworkToLocal { network: RequestResult<CoinData> ->
                println("CONVERTING NETWORK TO OUTPUT ===== $network")
                when (network) {
                    is RequestResult.Success -> {
                        val output = network.data
                        println("CONVERTED NETWORK TO OUTPUT $output")
                        output
                    }
                    is RequestResult.Exception -> {
                        throw DataConversionException("${network.error}")
                    }
                }
            }
            .fromOutputToLocal { output: CoinData ->
                //require(output is StoreOutput.Data.Single) { "Output to Local" }
                println("CONVERTED OUTPUT TO LOCAL $output")
                output
            }
            .build()
}
