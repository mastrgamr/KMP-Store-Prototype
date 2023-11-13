package com.example.kmptest

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CoinCapApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
                isLenient = true
            })
        }
    }

    suspend fun getCoinCapData(): RequestResult<CoinCapResponse> = try {
        val response = httpClient.get("https://api.coincap.io/v2/assets")
        RequestResult.Success(response.body())
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }
}
