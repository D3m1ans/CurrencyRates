package com.n1cks.core.data.remote.cbr

import com.n1cks.core.data.remote.response.RatesResponse
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET

val CbrJson = Json { ignoreUnknownKeys = true }

interface CbrApi {
    @GET("daily_json.js")
    suspend fun getDailyRates(): RatesResponse
}

fun createCbrApi(): CbrApi =
    Retrofit.Builder()
        .baseUrl("https://www.cbr-xml-daily.ru/")
        .addConverterFactory(CbrJson.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(CbrApi::class.java)