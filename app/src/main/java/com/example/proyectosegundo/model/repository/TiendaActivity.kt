package com.example.proyectosegundo.model.repository

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectosegundo.R
import com.example.proyectosegundo.model.data.Product
import com.example.proyectosegundo.model.data.ProductAdapter
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class TiendaActivity : AppCompatActivity() {

    private lateinit var rvProductos: RecyclerView
    private lateinit var etFiltro: EditText
    private lateinit var adapter: ProductAdapter
    private lateinit var layoutManager: GridLayoutManager
    private val productos = mutableListOf<Product>()
    private var lastVisible: DocumentSnapshot? = null

    private val dbRef = FirebaseFirestore.getInstance().collection("Productos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tienda)

        rvProductos = findViewById(R.id.rvProductos)
        etFiltro = findViewById(R.id.etFiltro)

        // Configurar el RecyclerView para 2 columnas
        layoutManager = GridLayoutManager(this, 2)
        rvProductos.layoutManager = layoutManager

        adapter = ProductAdapter(productos)
        rvProductos.adapter = adapter

        // Cargar productos
        cargarProductos()

        // Filtro en el EditText
        etFiltro.addTextChangedListener {
            cargarProductos(it.toString())
        }
    }

    private fun cargarProductos(filtro: String = "") {
        var query: Query = dbRef.limit(10)

        if (filtro.isNotEmpty()) {
            query = query.whereArrayContains("nombre", filtro)
        }

        query = query.orderBy("nombre")

        if (lastVisible != null) {
            query = query.startAfter(lastVisible!!)
        }

        query.get().addOnSuccessListener { result ->
            val nuevoProductos = mutableListOf<Product>()
            for (document in result) {
                val producto = document.toObject(Product::class.java)
                nuevoProductos.add(producto)
            }

            // Si hay productos, los agregamos a la lista
            if (nuevoProductos.isNotEmpty()) {
                lastVisible = result.documents[result.size() - 1]
                productos.addAll(nuevoProductos)
                adapter.notifyDataSetChanged()
            }
        }
    }

}