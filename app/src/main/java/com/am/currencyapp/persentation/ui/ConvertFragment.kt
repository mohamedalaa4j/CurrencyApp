package com.am.currencyapp.persentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.am.currencyapp.databinding.FragmentConvertBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConvertFragment : BindingFragment<FragmentConvertBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentConvertBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}