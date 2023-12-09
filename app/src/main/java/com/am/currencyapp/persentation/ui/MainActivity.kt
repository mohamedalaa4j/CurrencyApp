package com.am.currencyapp.persentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.am.currencyapp.BuildConfig
import com.am.currencyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val apiKey = BuildConfig.apiKey

    }
}