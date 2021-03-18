package com.fpiardi.monitoringservices.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fpiardi.monitoringservices.R
import com.fpiardi.monitoringservices.model.DetailsError

class DetailsErrorAdapter(private val list: List<DetailsError>, private val context: Context) :
    RecyclerView.Adapter<DetailsErrorAdapter.ItemHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_details_error, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView = itemView.findViewById<TextView>(R.id.name)
        private val dateTextView = itemView.findViewById<TextView>(R.id.date)
        private val timeTextView = itemView.findViewById<TextView>(R.id.time)

        fun bind(item: DetailsError) {
            nameTextView.text = normalizeName(item.name)
            dateTextView.text = item.date.substringBefore("T", item.date)
            timeTextView.text = item.date.substringAfter("T", String())
        }

        private fun normalizeName(name: String): String {
            val response = name.replace(oldValue = ";", newValue = "\\r\\n")

            return response
        }
    }

}