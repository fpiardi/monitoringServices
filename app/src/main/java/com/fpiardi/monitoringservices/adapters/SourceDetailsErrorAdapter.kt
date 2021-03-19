package com.fpiardi.monitoringservices.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fpiardi.monitoringservices.R
import com.fpiardi.monitoringservices.model.SourceDetailError

class SourceDetailsErrorAdapter(private val list: List<SourceDetailError>, private val context: Context) :
    RecyclerView.Adapter<SourceDetailsErrorAdapter.ItemHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_search_result, parent, false)
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
        private val sourceTextView = itemView.findViewById<TextView>(R.id.source)

        fun bind(item: SourceDetailError) {
            nameTextView.text = item.name
            dateTextView.text = item.date
            sourceTextView.text = item.source
        }
    }

}