package com.miempresa.ripter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SismoAdapter(private val SismoItems: List<SismoItem>) :
    RecyclerView.Adapter<SismoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.sis_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sismoItem = SismoItems[position]
        holder.bind(sismoItem)
    }

    override fun getItemCount(): Int {
        return SismoItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movimientoTextView: TextView = itemView.findViewById(R.id.text_intesidad)
        private val tiempoTextView: TextView = itemView.findViewById(R.id.text_fecha)

        fun bind(sismoItem: SismoItem) {
            movimientoTextView.text = sismoItem.movimiento
            tiempoTextView.text = sismoItem.tiempo
        }
    }
}
