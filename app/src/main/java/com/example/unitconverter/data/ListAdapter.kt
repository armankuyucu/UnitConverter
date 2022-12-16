package com.example.unitconverter.data

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.unitconverter.databinding.ItemBinding

class ListAdapter(val context: Context, var activity: TableActivity, var items: List<ItemsList>) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    inner class ListViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)
    var itemsList: MutableList<ItemsList> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(layoutInflater, parent, false)
        return ListViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.binding.apply {
            tvItem.text = items[position].item
            deleteButton.setOnClickListener {
                val dataModelArrayList = getDataModelList()

                val sqLiteHelper = SQLiteHelper(context)
                val index = Math.max(0, holder.bindingAdapterPosition)
                for (l in dataModelArrayList) {
                    println("i: ${items[index].item}")
                    println("l: ${l.date}")

                    if (items[index].item.startsWith(l.date)){
                        sqLiteHelper.deleteItem(l.id)
                        activity.updateTable()
                    }
                }
            }

        }

    }

    private fun getDataModelList(): ArrayList<DataModel> {
        val databaseHandler = SQLiteHelper(context)
        return databaseHandler.readTable()
    }

}