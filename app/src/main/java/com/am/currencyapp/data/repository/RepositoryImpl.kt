package com.am.currencyapp.data.repository

import com.am.currencyapp.BuildConfig
import com.am.currencyapp.data.remote.ApiService
import com.am.currencyapp.data.remote.dto.LatestRatesResponse
import com.am.currencyapp.domain.model.CurrencyRate
import com.am.currencyapp.domain.repository.Repository
import com.am.currencyapp.util.state.ApiState
import com.am.currencyapp.util.state.Resource
import com.am.currencyapp.util.state.UiText
import com.am.currencyapp.util.toResultFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.reflect.full.memberProperties

class RepositoryImpl(private val apiService: ApiService) : Repository {

    override suspend fun getLatestRates(): Flow<Resource<List<CurrencyRate>>> {
        return flow {
            val result = toResultFlow { apiService.getLatestRates(BuildConfig.apiKey) }
            result.collect { apiState ->
                when (apiState) {
                    is ApiState.Loading -> emit(Resource.Loading())
                    is ApiState.Error -> emit(Resource.Error(apiState.message!!))
                    is ApiState.Success -> {
                        if (apiState.data?.success == true) {
                            emit(
                                Resource.Success(
                                    toCurrenciesList(apiState.data)
                                )
                            )

                        } else {
                            emit(Resource.Error(UiText.DynamicString(apiState.data?.success.toString())))
                        }
                    }
                }
            }
        }
    }

    private fun toCurrenciesList(data: LatestRatesResponse): List<CurrencyRate> {
        val currenciesList = mutableListOf<CurrencyRate>()

        val currencies = LatestRatesResponse.Rates::class.memberProperties

        for (i in currencies) {
            val currencyName = i.name
            val currencyRate = i.get(data.rates!!)

            currenciesList.add(
                CurrencyRate(currencyName.toUpperCase(), currencyRate.toString().toDouble())
            )
        }
        return currenciesList
    }

}