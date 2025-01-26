package com.example.proyectosegundo.model.repository

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectosegundo.R
import com.example.proyectosegundo.model.data.Product
import com.example.proyectosegundo.model.data.ProductAdapter
import com.example.proyectosegundo.model.data.ProductDetailActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class TiendaActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private val productos: MutableList<Product> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tienda)

        initComponents()
        loadProductsFromFirebase()
    }

    private fun initComponents() {
        recyclerView = findViewById(R.id.recyclerViewTienda)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // Mostrar en 2 columnas
        productAdapter = ProductAdapter(productos) { product ->
            // Manejar el clic en un producto
            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                putExtra("productoId", product.productoId)
                putExtra("nombre", product.nombre)
                putExtra("categoria", product.categoria)
                putExtra("precio", product.precio)
                putExtra("stock", product.stock)
            }
            startActivity(intent)
        }
        recyclerView.adapter = productAdapter
    }

    private fun loadProductsFromFirebase() {
        val dbRef = FirebaseDatabase.getInstance().getReference("Productos")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productos.clear() // Limpiar la lista antes de agregar nuevos datos
                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    product?.let { productos.add(it) }
                }
                productAdapter.notifyDataSetChanged() // Actualizar la lista en el RecyclerView
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TiendaActivity, "Error al cargar productos: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}