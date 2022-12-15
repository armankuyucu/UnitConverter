package com.example.unitconverter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.unitconverter.databinding.ActivityLenghtConverterBinding

class LengthConverterActivity : ConverterActivity() {
    private lateinit var binding: ActivityLenghtConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLenghtConverterBinding.inflate(layoutInflater)
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
                if (binding.textViewOutput.text.isNotEmpty()) {
                    convert()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.swapSpinner.setOnClickListener {
            swapSpinnerTexts(binding.spinnerInput,binding.spinnerOutput)
        }
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
            "kilometre" to 100000.0,
            "nanometre" to 0.0000001
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
        binding.textViewOutput.text = convertedValue.toString()
    }

}