package com.am.currencyapp.persentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.currencyapp.data.remote.dto.LatestRatesResponse
import com.am.currencyapp.domain.model.CurrencyRate
import com.am.currencyapp.domain.usecase.GetLatestRatesUseCase
import com.am.currencyapp.util.state.Resource
import com.am.currencyapp.util.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ConvertViewModel @Inject constructor(
    private val getLatestRatesUseCase: GetLatestRatesUseCase,
) : ViewModel() {

    private val _getLatestRatesState = MutableStateFlow<UiState<List<CurrencyRate>>>(UiState.Empty())
    val getLatestRatesState: StateFlow<UiState<List<CurrencyRate>>> = _getLatestRatesState

    private var getLatestRatesJob: Job? = null

    private fun getLatestRates() {
        getLatestRatesJob?.cancel()
        getLatestRatesJob = viewModelScope.launch {
            withContext(coroutineContext) {
                getLatestRatesUseCase.getContactUsInfo().collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _getLatestRatesState.value = UiState.Success(result.data)
                        }

                        is Resource.Error -> {
                            _getLatestRatesState.value = UiState.Error(result.message!!)
                        }

                        is Resource.Loading -> {
                            _getLatestRatesState.value = UiState.Loading()
                        }
                    }
                }
            }
        }
    }

    init {
        getLatestRates()
    }
}