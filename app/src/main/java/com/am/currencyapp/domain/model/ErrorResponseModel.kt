package com.am.currencyapp.domain.model


data class ErrorResponseModel(
    val success: Boolean,
    val error: Error
) {
    data class Error(
        val code: Int,
        val type: String,
        val info: String
    )
}