package com.example.proyectosegundo.model.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectosegundo.R

class ProductAdapter(private val productos: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productos[position])
    }

    override fun getItemCount(): Int = productos.size

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivMiniatura: ImageView = itemView.findViewById(R.id.ivMiniatura)
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        private val tvStock: TextView = itemView.findViewById(R.id.tvStock)

        fun bind(product: Product) {
            // Cargar miniatura con Glide o Picasso
            Glide.with(itemView.context)
                .load(product.miniaturaUrl)  // Usar el campo miniaturaUrl de Product
                .into(ivMiniatura)

            tvNombre.text = product.nombre  // Usar el campo nombre de Product
            tvStock.text = "Stock: ${product.stock}"  // Usar el campo stock de Product
        }
    }
}