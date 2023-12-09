package com.am.currencyapp.data.remote

import com.am.currencyapp.data.remote.dto.LatestRatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL = "http://data.fixer.io/api/"
    }

    @GET("latest")
    suspend fun getLatestRates(
        @Query("access_key") apiKey: String,
    ): Response<LatestRatesResponse>
}