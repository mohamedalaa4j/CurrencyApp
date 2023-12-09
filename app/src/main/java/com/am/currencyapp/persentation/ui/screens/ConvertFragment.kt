package com.am.currencyapp.persentation.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.am.currencyapp.R
import com.am.currencyapp.databinding.FragmentConvertBinding
import com.am.currencyapp.domain.model.CurrencyRate
import com.am.currencyapp.persentation.ui.BindingFragment
import com.am.currencyapp.persentation.ui.adapter.SpinnerFromAdapter
import com.am.currencyapp.persentation.ui.dialog.LoadingDialog
import com.am.currencyapp.persentation.viewmodel.ConvertViewModel
import com.am.currencyapp.util.showToast
import com.am.currencyapp.util.state.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConvertFragment : BindingFragment<FragmentConvertBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentConvertBinding::inflate

    private val viewModel by viewModels<ConvertViewModel>()

    private var toCurrencyRate = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupConverting()
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

                            val data = state.data
                            data?.let {
                                setupSpinnerFrom()
                                setupSpinnerTo(data)
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

    private fun setupSpinnerTo(data: List<CurrencyRate>) {
        binding.apply {
            val list = data.toMutableList()

            list.add(
                0,
                CurrencyRate(getString(R.string.to), -0.0)
            )

            val toSpinnerAdapter = SpinnerFromAdapter(requireContext(), list)
            spinnerTo.adapter = toSpinnerAdapter

            spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val model = parent?.getItemAtPosition(position) as CurrencyRate
                    toCurrencyRate = model.rate

                    binding.etOutput.setText("")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }
    }

    private fun setupSpinnerFrom() {
        binding.apply {
            val list = listOf<CurrencyRate>(CurrencyRate("EUR", -0.0))

            val fromSpinnerAdapter = SpinnerFromAdapter(requireContext(), list)
            spinnerFrom.adapter = fromSpinnerAdapter

            spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }
    }

    private fun setupConverting() {
        binding.apply {
            btn.setOnClickListener {
                if (etInput.text.toString().isNullOrEmpty()) {
                    showToast(getString(R.string.please_enter_an_amount))
                } else if (toCurrencyRate == -0.0) {
                    showToast(getString(R.string.please_choose_current_to_convert_into))
                } else {
                    val input = etInput.text.toString().trim().toDouble()
                    val output = input * toCurrencyRate
                    etOutput.setText(output.toString())
                }
            }

        }
    }

}