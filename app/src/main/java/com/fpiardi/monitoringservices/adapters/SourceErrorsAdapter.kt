package com.fpiardi.monitoringservices.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fpiardi.monitoringservices.R
import com.fpiardi.monitoringservices.model.SourceErrors

class SourceErrorsAdapter(private val list: List<SourceErrors>, private val context: Context) :
    RecyclerView.Adapter<SourceErrorsAdapter.ItemHolder>()  {

    var onItemClick: ((SourceErrors) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_source_error, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sourceTextView = itemView.findViewById<TextView>(R.id.source)
        private val noErrorsTextView = itemView.findViewById<TextView>(R.id.noErrors)

        fun bind(item: SourceErrors) {
            sourceTextView.text = item.source
            noErrorsTextView.text = item.noErrors.toString().trim()
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(list[adapterPosition])
            }
        }
    }

}