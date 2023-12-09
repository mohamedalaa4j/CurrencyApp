package com.am.currencyapp.persentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.am.currencyapp.data.remote.dto.LatestRatesResponse
import com.am.currencyapp.databinding.FragmentConvertBinding
import com.am.currencyapp.persentation.ui.dialog.LoadingDialog
import com.am.currencyapp.persentation.viewmodel.ConvertViewModel
import com.am.currencyapp.util.showToast
import com.am.currencyapp.util.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlin.time.times

@AndroidEntryPoint
class ConvertFragment : BindingFragment<FragmentConvertBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentConvertBinding::inflate

    private val viewModel by viewModels<ConvertViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchData()
    }

    private fun fetchData() {
        fetchLatestRatesState()
    }

    private fun fetchLatestRatesState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getLatestRatesState.collect { state ->
                    when (state) {

                        is UiState.Success -> {
                            LoadingDialog.dismissDialog()

                            Log.e("dataFromApi", state.data?.rates.toString())

                            val data = state.data

                            data?.let {
                                convert(it)
                            }
                        }

                        is UiState.Error -> {
                            LoadingDialog.dismissDialog()
                            val errorMessage = state.message!!.asString(requireContext())
                            showToast(errorMessage)
                        }

                        is UiState.Loading -> {
                            LoadingDialog.showDialog()
                        }

                        else -> {}
                    }

                }
            }
        }
    }

    private fun convert(data: LatestRatesResponse) {
        binding.apply {
            btn.setOnClickListener {
                val input = etInput.text.toString().trim().toDouble()
                val output = input * data.rates?.aED!!
                etOutput.setText(output.toString())

            }

        }
    }

}