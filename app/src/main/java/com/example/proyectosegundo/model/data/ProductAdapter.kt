package com.example.proyectosegundo.model.data

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectosegundo.R

class ProductAdapter(
    private val productos: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productos[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }

    override fun getItemCount(): Int = productos.size

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivMiniatura: ImageView = itemView.findViewById(R.id.ivMiniatura)
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)

        fun bind(product: Product) {
            Glide.with(itemView.context)
                .load(Uri.parse(product.miniaturaUrl)) // Cargar URI desde Firebase
                .placeholder(R.drawable.placeholder_image) // Imagen temporal mientras carga
                .error(R.drawable.error_image) // Imagen en caso de error
                .into(ivMiniatura)
            tvNombre.text = product.nombre
        }
    }
}