package com.example.unitconverter.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import com.example.unitconverter.databinding.ActivityVolumeConverterBinding

class VolumeConverterActivity : ConverterActivity() {
    private lateinit var binding: ActivityVolumeConverterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVolumeConverterBinding.inflate(layoutInflater)
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
            swapSpinnerTexts(binding.spinnerInput,binding.spinnerOutput)
        }
        editSupportActionBar(this, "Hacim Dönüştürücü")

    }

    fun convertVolume(value: Double, fromUnit: String, toUnit: String): Double {
        // Define the conversion factors
        val conversionFactors = mapOf(
            "mikrolitre" to 0.000001,
            "mililitre" to 0.001,
            "litre" to 1.0,
            "metre küp" to 1000.0,
            "galon" to 3.78541,
            "pint" to 0.473176,
            "quart" to 0.946353,
            "cubic inch" to 0.0163871
        )

        // Get the conversion factors for the input units
        val fromFactor = conversionFactors.getValue(fromUnit.lowercase())
        val toFactor = conversionFactors.getValue(toUnit.lowercase())

        // Convert the volume using the conversion factors
        return value * fromFactor / toFactor
    }

    fun convert() {
        val input = getEditTextInput(binding.editTextInput)
        val from = getSpinnerSelection(binding.spinnerInput)
        val to = getSpinnerSelection(binding.spinnerOutput)
        val convertedValue = convertVolume(input, from, to)
        binding.textViewOutput.text = convertedValue.toString()
    }

    fun saveResult(){
        val input = getEditTextInput(binding.editTextInput)
        val from = getSpinnerSelection(binding.spinnerInput)
        val to = getSpinnerSelection(binding.spinnerOutput)
        val convertedValue = convertVolume(input, from, to)
        binding.textViewOutput.text = convertedValue.toString()
        saveToDatabase(this,input,from,to,convertedValue)
    }

}