package com.aglia.doctorchatbot.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aglia.doctorchatbot.databinding.SettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: SettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickEvents()
    }

    private fun clickEvents() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }
}