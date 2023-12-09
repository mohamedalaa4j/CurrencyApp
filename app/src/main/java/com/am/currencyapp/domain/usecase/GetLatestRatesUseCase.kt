package com.am.currencyapp.domain.usecase

import com.am.currencyapp.data.remote.dto.LatestRatesResponse
import com.am.currencyapp.domain.repository.Repository
import com.am.currencyapp.util.state.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLatestRatesUseCase @Inject constructor(private val repository: Repository) {
    suspend fun getContactUsInfo(): Flow<Resource<LatestRatesResponse>> {
        return repository.getLatestRates()
    }
}