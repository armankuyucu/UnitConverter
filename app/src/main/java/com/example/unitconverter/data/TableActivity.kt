package com.example.unitconverter.data

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unitconverter.databinding.ActivityTableBinding

class TableActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTableBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTableBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addToTable()
        val sqLiteHelper = SQLiteHelper(this)
        binding.button.setOnClickListener {
            sqLiteHelper.deleteTable()
            addToTable()
        }
    }

    fun addToTable() {
        val dataModelArrayList = getDataModelList()

        var itemsList: MutableList<ItemsList> = mutableListOf()

        for (l in dataModelArrayList) {
            itemsList.add(ItemsList("${l.date} tarihinde ${l.value} değeri,${l.from} biriminden ${l.to} biriminine dönüştürülerek ${l.result} sonucu elde edildi."))
        }

        val adapter = ListAdapter(itemsList)
        binding.rvTable.adapter = adapter
        binding.rvTable.layoutManager = LinearLayoutManager(this)
    }

    private fun getDataModelList(): ArrayList<DataModel> {
        val databaseHandler = SQLiteHelper(this)
        return databaseHandler.readTable()
    }
}