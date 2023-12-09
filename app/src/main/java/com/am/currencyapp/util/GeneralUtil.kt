package com.am.currencyapp.util

import android.util.Log
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.am.currencyapp.R
import com.am.currencyapp.domain.model.ErrorResponseModel
import com.am.currencyapp.util.state.ApiState
import com.am.currencyapp.util.state.UiText
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.onBackPressed(code: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
        code()
    }
}

fun <T> toResultFlow(call: suspend () -> Response<T>): Flow<ApiState<T>> = flow {
    emit(ApiState.Loading())
    try {
        val response = call()
        if (response.isSuccessful) {
            emit(ApiState.Success(response.body()))
            Log.e("networkResponse", "Success\n" + response.body().toString())

        } else {
            val errorBody = response.errorBody()?.string()
            val errorObject = Gson().fromJson(errorBody, ErrorResponseModel::class.java)
            emit(ApiState.Error(UiText.DynamicString(errorObject.error.info)))
            Log.e("networkResponse", "Failure\n $errorBody")
        }

    } catch (e: HttpException) {
        Log.e("networkResponse", "HttpException\n ${e.message.toString()}")
        emit(ApiState.Error(UiText.StringResource(R.string.something_went_wrong)))

    } catch (e: IOException) {
        Log.e("networkResponse", "IOException\n ${e.message.toString()}")
        emit(ApiState.Error(UiText.StringResource(R.string.check_your_internet_connection)))

    } catch (e: Exception) {
        Log.e("networkResponse", "Exception\n ${e.message.toString()}")
        emit(ApiState.Error(UiText.StringResource(R.string.something_went_wrong)))
    }
}