package com.example.unitconverter.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import com.example.unitconverter.databinding.ActivityAreaConverterBinding

class AreaConverterActivity : ConverterActivity() {
    private lateinit var binding:ActivityAreaConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAreaConverterBinding.inflate(layoutInflater)
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
        editSupportActionBar(this, "Alan Dönüştürücü")

    }

    fun convertArea(value: Double, fromUnit: String, toUnit: String): Double {

        val conversionFactors = mapOf(
            "milimetre kare" to 0.000001,
            "santimetre kare" to 0.0001,
            "metre kare" to 1.0,
            "hektar" to 10000.0,
            "kilometre kare" to 1000000.0,
            "inch kare" to 0.00064516,
            "foot kare" to 0.09290304,
            "yard kare" to 0.83612736,
            "mil kare" to 2589988.110336,
            "akre" to 4046.8564224
        )

        val fromFactor = conversionFactors.getValue(fromUnit.lowercase())
        val toFactor = conversionFactors.getValue(toUnit.lowercase())

        return value * fromFactor / toFactor
    }

    fun convert() {
        val input = getEditTextInput(binding.editTextInput)
        val from = getSpinnerSelection(binding.spinnerInput)
        val to = getSpinnerSelection(binding.spinnerOutput)
        val convertedValue = convertArea(input, from, to)
        binding.textViewOutput.text = convertedValue.toString()
    }

    fun saveResult(){
        val input = getEditTextInput(binding.editTextInput)
        val from = getSpinnerSelection(binding.spinnerInput)
        val to = getSpinnerSelection(binding.spinnerOutput)
        val convertedValue = convertArea(input, from, to)
        binding.textViewOutput.text = convertedValue.toString()
        saveToDatabase(this,input,from,to,convertedValue)
    }

}