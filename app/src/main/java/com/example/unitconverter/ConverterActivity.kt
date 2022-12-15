package com.example.unitconverter

import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

open class ConverterActivity : AppCompatActivity(){
    fun getSpinnerSelection(spinner: Spinner): String {
        // Get the selected item from the spinner
        return spinner.selectedItem.toString()
    }

    fun getEditTextInput(editText: EditText): Double {
        // Get the text from the editText
        return editText.text.toString().toDouble()
    }

    fun swapSpinnerTexts(spinnerInput: Spinner, spinnerOutput: Spinner) {
        val temp = spinnerOutput.selectedItemPosition
        spinnerOutput.setSelection(spinnerInput.selectedItemPosition)
        spinnerInput.setSelection(temp)
    }
}