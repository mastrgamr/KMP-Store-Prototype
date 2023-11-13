package com.example.kmptest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
{
    "data": [
        {
            "id": "bitcoin",
            "rank": "1",
            "symbol": "BTC",
            "name": "Bitcoin",
            "supply": "19541456.0000000000000000",
            "maxSupply": "21000000.0000000000000000",
            "marketCapUsd": "719569994296.2507136205994256",
            "volumeUsd24Hr": "4962829673.9769939769923247",
            "priceUsd": "36822.7420871940511301",
            "changePercent24Hr": "-0.8765136502244545",
            "vwap24Hr": "36910.2081291988605388",
            "explorer": "https://blockchain.info/"
        },
        {
            "id": "ethereum",
            "rank": "2",
            "symbol": "ETH",
            "name": "Ethereum",
            "supply": "120260281.4350353200000000",
            "maxSupply": null,
            "marketCapUsd": "251995529157.0355180795059801",
            "volumeUsd24Hr": "4997111486.9749993086046529",
            "priceUsd": "2095.4177568024706654",
            "changePercent24Hr": "2.2489988691209240",
            "vwap24Hr": "2069.6602278444290712",
            "explorer": "https://etherscan.io/"
        }
    ]
}
*/

@Serializable
data class CoinCapResponse(
    @SerialName("data")
    val coinDataList: List<CoinData>,
)

@Serializable
data class CoinData(
    @SerialName("id") val id: String,
    @SerialName("symbol") val symbol: String,
    @SerialName("name") val name: String,
    @SerialName("priceUsd") val priceUsd: String,
    @SerialName("changePercent24Hr") val changePercent24Hr: String
)
