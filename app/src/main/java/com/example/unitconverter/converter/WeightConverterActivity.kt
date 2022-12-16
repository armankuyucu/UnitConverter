package com.example.unitconverter.converter

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import com.example.unitconverter.R
import com.example.unitconverter.data.TableActivity
import com.example.unitconverter.databinding.ActivityWeightConverterBinding

class WeightConverterActivity : ConverterActivity() {
    private lateinit var binding: ActivityWeightConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeightConverterBinding.inflate(layoutInflater)

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

        binding.saveToDatabaseButton.setOnClickListener {
            saveResult()
        }

        editSupportActionBar(this, "Ağırlık Dönüştürücü")
    }

    fun convertWeight(value: Double, fromUnit: String, toUnit: String): Double {
        val conversionFactors = mapOf(
            "mikrogram" to 0.000001,
            "miligram" to 0.001,
            "santigram" to 0.01,
            "desigram" to 0.1,
            "gram" to 1.0,
            "dekagram" to 10.0,
            "hektogram" to 100.0,
            "kilogram" to 1000.0,
            "megagram" to 1000000.0,
            "ons" to 28.3495,
            "pound" to 453.592,
            "stone" to 6350.29,
            "ton" to 1000000.0,
        )

        val fromFactor = conversionFactors.getValue(fromUnit.lowercase())
        val toFactor = conversionFactors.getValue(toUnit.lowercase())

        return value * fromFactor / toFactor
    }

    fun convert() {
        val input = getEditTextInput(binding.editTextInput)
        val from = getSpinnerSelection(binding.spinnerInput)
        val to = getSpinnerSelection(binding.spinnerOutput)
        val convertedValue = convertWeight(input, from, to)
        binding.textViewOutput.text = convertedValue.toString()
    }

    fun saveResult() {
        val input = getEditTextInput(binding.editTextInput)
        val from = getSpinnerSelection(binding.spinnerInput)
        val to = getSpinnerSelection(binding.spinnerOutput)
        val convertedValue = convertWeight(input, from, to)
        binding.textViewOutput.text = convertedValue.toString()
        saveToDatabase(this, input, from, to, convertedValue)
    }

}