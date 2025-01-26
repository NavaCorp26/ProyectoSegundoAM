package com.example.proyectosegundo.model.data

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectosegundo.R

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var tvNombreProducto: TextView
    private lateinit var tvCategoriaProducto: TextView
    private lateinit var tvPrecioProducto: TextView
    private lateinit var tvStockProducto: TextView
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        initComponents()
        showDetails()

        btnDelete.setOnClickListener {
            deleteProduct(intent.getStringExtra("productoId").toString())
        }
    }

    private fun initComponents() {
        tvNombreProducto = findViewById(R.id.tvNombreProductoDetalle)
        tvCategoriaProducto = findViewById(R.id.tvCategoriaProductoDetalle)
        tvPrecioProducto = findViewById(R.id.tvPrecioProductoDetalle)
        tvStockProducto = findViewById(R.id.tvStockProductoDetalle)
        btnEdit = findViewById(R.id.btnEditProduct)
        btnDelete = findViewById(R.id.btnDeleteProduct)
    }

    private fun showDetails() {
        tvNombreProducto.text = intent.getStringExtra("nombre")
        tvCategoriaProducto.text = intent.getStringExtra("categoria")
        tvPrecioProducto.text = "Precio: ${intent.getStringExtra("precio")}"
        tvStockProducto.text = "Stock: ${intent.getIntExtra("stock", 0)}"
    }

    private fun deleteProduct(productId: String) {
        // Implementar lógica para eliminar el producto
    }
}