package com.am.currencyapp.domain.model

data class RatesModel (
    val baseCurrency:String,
    val currencies:List<CurrencyRate>
)