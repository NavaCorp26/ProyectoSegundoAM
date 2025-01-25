package com.example.proyectosegundo.model.data

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectosegundo.R

class UsuarioAdapter(private val usuarioList: ArrayList<Usuario>) :
    RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.usuario_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return usuarioList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUsuario = usuarioList[position]
        holder.tvNombreUsuario.text = currentUsuario.username
        holder.tvEmail.text = currentUsuario.userEmail
    }

    class ViewHolder(itemView: android.view.View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val tvNombreUsuario: TextView = itemView.findViewById(R.id.tvNombreUsuario)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}