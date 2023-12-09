package com.am.currencyapp.persentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.am.currencyapp.databinding.SpinnerItemBinding
import com.am.currencyapp.domain.model.CurrencyRate


class SpinnerFromAdapter(context: Context, list: List<CurrencyRate>) :
    ArrayAdapter<CurrencyRate>(context, 0, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: SpinnerItemBinding
        var row = convertView

        if (row == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = SpinnerItemBinding.inflate(inflater, parent, false)
            row = binding.root
        } else {
            binding = SpinnerItemBinding.bind(row)
        }

        binding.apply {

            tv.text = getItem(position)?.currency
        }

        return row
    }
}