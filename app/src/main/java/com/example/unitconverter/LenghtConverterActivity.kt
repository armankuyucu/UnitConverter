package com.example.unitconverter

import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.unitconverter.databinding.ActivityLenghtConverterBinding

class LenghtConverterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLenghtConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLenghtConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView2.setOnClickListener {
            convert()
        }
    }

    fun getSpinnerSelection(spinner: Spinner): String {
        // Get the selected item from the spinner
        val spinnerSelection = spinner.selectedItem.toString()
        return spinnerSelection
    }

    fun getEditTextInput(editText: EditText): Double {
        // Get the text from the editText
        val inputString = editText.text.toString()
        // Convert the text to a Double
        val input = inputString.toDouble()
        return input
    }

    fun convertLength(value: Double, fromUnit: String, toUnit: String): Double {
        val conversionFactors = mapOf(
            "inch" to 2.54,
            "foot" to 30.48,
            "yard" to 91.44,
            "mil" to 160934.4,
            "milimetre" to 0.1,
            "santimetre" to 1.0,
            "metre" to 100.0,
            "kilometre" to 100000.0
        )

        val fromFactor = conversionFactors.getValue(fromUnit.lowercase())
        val toFactor = conversionFactors.getValue(toUnit.lowercase())

        return value * fromFactor / toFactor
    }

    fun convert() {
        val input = getEditTextInput(binding.editTextInput)
        val from = getSpinnerSelection(binding.spinnerInput)
        val to = getSpinnerSelection(binding.spinnerOutput)
        val convertedValue = convertLength(input, from, to)
        binding.editTextOutput.setText(convertedValue.toString())
    }
}