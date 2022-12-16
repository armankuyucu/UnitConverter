package com.example.unitconverter.converter

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.unitconverter.R
import com.example.unitconverter.data.TableActivity
import com.example.unitconverter.databinding.ActivityLenghtConverterBinding
import java.text.SimpleDateFormat
import java.util.*

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

        editSupportActionBar(this, "Uzunluk Dönüştürücü")
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

    fun convertLength(value: Double, fromUnit: String, toUnit: String): Double {
        val conversionFactors = mapOf(
            "inch" to 2.54,
            "foot" to 30.48,
            "yard" to 91.44,
            "mil" to 160934.4,
            "mikrometre" to 0.0001,
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

    fun saveResult() {
        val input = getEditTextInput(binding.editTextInput)
        val from = getSpinnerSelection(binding.spinnerInput)
        val to = getSpinnerSelection(binding.spinnerOutput)
        val convertedValue = convertLength(input, from, to)
        binding.textViewOutput.text = convertedValue.toString()
        saveToDatabase(this, input, from, to, convertedValue)
    }
}