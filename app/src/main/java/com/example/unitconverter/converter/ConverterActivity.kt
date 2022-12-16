package com.example.unitconverter.converter

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.unitconverter.R
import com.example.unitconverter.data.DataModel
import com.example.unitconverter.data.SQLiteHelper
import com.example.unitconverter.data.TableActivity
import java.text.SimpleDateFormat
import java.util.*

open class ConverterActivity : AppCompatActivity() {
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

    fun saveToDatabase(context: Context, value: Double, from: String, to: String, result: Double) {
        // Get Current Time
        val simpleDataFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        val date: String = simpleDataFormat.format(Date())
        val sqLiteHelper = SQLiteHelper(context)

        if (!value.isNaN() and from.isNotEmpty() and to.isNotEmpty() and !result.isNaN()) {
            val status =
                sqLiteHelper.addData(DataModel(0, value, from, to, result, date))
            if (status > -1) {
                Toast.makeText(applicationContext, "Kaydedildi", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Kaydedilemedi!",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    fun editSupportActionBar(context: Context, title: String){
        // Get the support action bar
        val actionBar = supportActionBar

        // Set the title for the action bar
        actionBar?.title = title

        // Set the icon for the ImageView
        val icon = ContextCompat.getDrawable(context, R.drawable.history_icon)

        // Create a LinearLayout to hold the ImageView and the title
        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.HORIZONTAL


        // Create the TextView for the title and add it to the LinearLayout
        val textView = TextView(context)
        textView.text = actionBar?.title
        textView.textSize = 25.0F
        textView.gravity = Gravity.CENTER
        textView.setTextColor(ContextCompat.getColor(context, R.color.white))

        // Set the margin for the TextView
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 256, 0)  // left, top, right, bottom
        textView.layoutParams = params

        linearLayout.addView(textView)
        // Create the ImageView and add it to the LinearLayout
        val imageView = ImageView(context)
        imageView.setImageDrawable(icon)
        linearLayout.addView(imageView)

        // Set the LinearLayout as the action bar's custom view
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.customView = linearLayout
        // Set the action bar's display options
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        // Add a click listener to the ImageView
        imageView.setOnClickListener {
            // Start the new activity here
            val intent = Intent(context, TableActivity::class.java)
            startActivity(intent)
        }

    }

}