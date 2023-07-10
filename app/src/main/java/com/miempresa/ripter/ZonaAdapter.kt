package com.miempresa.ripter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ZonaAdapter(private val ZonaItems: List<ZonaItem>) :
    RecyclerView.Adapter<ZonaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.zon_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val zonaItem = ZonaItems[position]
        holder.bind(zonaItem)
    }

    override fun getItemCount(): Int {
        return ZonaItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.text_nombre)
        private val empresaTextView: TextView = itemView.findViewById(R.id.text_company)

        fun bind(zonaItem: ZonaItem) {
            nombreTextView.text = zonaItem.nombre
            empresaTextView.text = zonaItem.empresaId
        }
    }
}