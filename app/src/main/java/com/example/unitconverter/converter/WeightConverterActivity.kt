package com.example.unitconverter.converter

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import com.example.unitconverter.R
import com.example.unitconverter.data.TableActivity
import com.example.unitconverter.databinding.ActivityWeightConverterBinding
import java.text.SimpleDateFormat
import java.util.*

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
        binding.saveToFileButton.setOnClickListener {
            // Check if the app has the WRITE_EXTERNAL_STORAGE permission
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                // If the app doesn't have the permission, request it
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_WRITE_EXTERNAL_STORAGE
                )
            } else {
                // If the app already has the permission, create the text file
                val simpleDataFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                val date: String = simpleDataFormat.format(Date())

                createTextFile(
                    date,
                    binding.editTextInput.text.toString(),
                    binding.spinnerInput.selectedItem.toString(),
                    binding.spinnerOutput.selectedItem.toString(),
                    binding.textViewOutput.text.toString()
                )
            }
        }

        editSupportActionBar(this, "Ağırlık Dönüştürücü")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            // Check if the permission was granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // The permission was granted, create the text file
                val simpleDataFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                val date: String = simpleDataFormat.format(Date())

                createTextFile(
                    date,
                    binding.editTextInput.text.toString(),
                    binding.spinnerInput.selectedItem.toString(),
                    binding.spinnerOutput.selectedItem.toString(),
                    binding.textViewOutput.text.toString()
                )
            } else {
                // The permission was denied, show a message to the user
                Toast.makeText(
                    this,
                    "Dosyanın yaratılması için WRITE_EXTERNAL_STORAGE iznine gerek duyuluyor.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
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