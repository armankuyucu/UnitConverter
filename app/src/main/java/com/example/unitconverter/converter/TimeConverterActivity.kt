package com.example.unitconverter.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import com.example.unitconverter.databinding.ActivityTimeConverterBinding

class TimeConverterActivity : ConverterActivity() {
    private lateinit var binding: ActivityTimeConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    convert()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.spinnerInput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // This method is called whenever an item is selected in the Spinner
                if (binding.editTextInput.text.isNotEmpty()) {
                    convert()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.spinnerOutput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // This method is called whenever an item is selected in the Spinner
                if (binding.editTextInput.text.isNotEmpty()) {
                    convert()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.swapSpinner.setOnClickListener {
            swapSpinnerTexts(binding.spinnerInput, binding.spinnerOutput)
        }

        editSupportActionBar(this, "Zaman Dönüştürücü")
    }

    fun convertTime(value: Double, fromUnit: String, toUnit: String): Double {
        // Define the conversion factors
        val conversionFactors = mapOf(
            "nano saniye" to 0.000000001,
            "mikro saniye" to 0.000001,
            "mili saniye" to 0.001,
            "saniye" to 1.0,
            "dakika" to 60.0,
            "saat" to 3600.0,
            "gün" to 86400.0,
            "hafta" to 604800.0,
            "ay" to 2629746.0,
            "yıl" to 31556952.0
        )

        // Get the conversion factors for the input units
        val fromFactor = conversionFactors.getValue(fromUnit.lowercase())
        val toFactor = conversionFactors.getValue(toUnit.lowercase())

        // Convert the time using the conversion factors
        return value * fromFactor / toFactor
    }

    fun convert() {
        val input = getEditTextInput(binding.editTextInput)
        val from = getSpinnerSelection(binding.spinnerInput)
        val to = getSpinnerSelection(binding.spinnerOutput)
        val convertedValue = convertTime(input, from, to)
        binding.textViewOutput.text = convertedValue.toString()
    }

    fun saveResult() {
        val input = getEditTextInput(binding.editTextInput)
        val from = getSpinnerSelection(binding.spinnerInput)
        val to = getSpinnerSelection(binding.spinnerOutput)
        val convertedValue = convertTime(input, from, to)
        binding.textViewOutput.text = convertedValue.toString()
        saveToDatabase(this, input, from, to, convertedValue)
    }

}