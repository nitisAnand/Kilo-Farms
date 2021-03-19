package com.nitish.kilofarms.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nitish.kilofarms.DataX
import com.nitish.kilofarms.R
import kotlinx.android.synthetic.main.rv_item_layout.view.*

class Adapter(val context: Context, val result: List<DataX>): RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var skuId: TextView
        var skuName: TextView

        init {
            skuId = itemView.skuId
            skuName = itemView.skuName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.rv_item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.skuId.text = result[position].skuId
        holder.skuName.text = result[position].skuName
    }

    override fun getItemCount(): Int {
        return result.size
    }
}