package com.miempresa.ripter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmpresaAdapter(private val empresaItems: List<EmpresaItem>) :
    RecyclerView.Adapter<EmpresaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.em_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val empresaItem = empresaItems[position]
        holder.bind(empresaItem)
    }

    override fun getItemCount(): Int {
        return empresaItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.text_nombre)
        private val telefonoTextView: TextView = itemView.findViewById(R.id.text_telefono)

        fun bind(empresaItem: EmpresaItem) {
            nombreTextView.text = empresaItem.nombre
            telefonoTextView.text = empresaItem.telefono
        }
    }
}
