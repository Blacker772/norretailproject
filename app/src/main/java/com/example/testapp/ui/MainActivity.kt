package com.example.testapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.R

import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.ui.log_in.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}