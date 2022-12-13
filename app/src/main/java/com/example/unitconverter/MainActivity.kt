package com.example.unitconverter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.unitconverter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lenghtImage.setOnClickListener {
            Intent(this, LengthConverterActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.areaImage.setOnClickListener {
            Intent(this, AreaConverterActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.weightImage.setOnClickListener {
            Intent(this, WeightConverterActivity::class.java).also {
                startActivity(it)
            }
        }
    }


}