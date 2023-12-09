package com.am.currencyapp.domain.repository

import com.am.currencyapp.data.remote.dto.LatestRatesResponse
import com.am.currencyapp.domain.model.CurrencyRate
import com.am.currencyapp.util.state.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getLatestRates(): Flow<Resource<List<CurrencyRate>>>
}