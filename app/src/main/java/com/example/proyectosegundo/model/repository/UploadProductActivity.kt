package com.example.proyectosegundo.model.repository

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectosegundo.R
import com.example.proyectosegundo.model.data.ImageAdapter
import com.example.proyectosegundo.model.data.Product
import com.google.firebase.database.FirebaseDatabase

class UploadProductActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etCategoria: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etStock: EditText
    private lateinit var btnSeleccionarMiniatura: Button
    private lateinit var btnSubirImagenes: Button
    private lateinit var btnGuardarProducto: Button
    private lateinit var ivMiniatura: ImageView
    private lateinit var rvImagenes: RecyclerView

    private var miniaturaUrl: String? = null
    private val imagenesUrls = mutableListOf<String>()
    private lateinit var adapter: ImageAdapter

    private val selectMiniaturaLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            miniaturaUrl = it.toString() // Guardamos la URL de la miniatura seleccionada
            Glide.with(this).load(it).into(ivMiniatura)  // Mostrar la miniatura en la vista previa
        }
    }

    private val selectImagesLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
        uris?.let {
            imagenesUrls.clear()
            it.forEach { uri ->
                imagenesUrls.add(uri.toString()) // Guardamos las URLs de las imágenes seleccionadas
            }
            adapter.notifyDataSetChanged()  // Actualiza el RecyclerView con las imágenes seleccionadas
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_product)

        initComponents()

        btnSeleccionarMiniatura.setOnClickListener { seleccionarMiniatura() }
        btnSubirImagenes.setOnClickListener { seleccionarImagenes() }
        btnGuardarProducto.setOnClickListener { guardarProducto() }
    }

    private fun initComponents() {
        etNombre = findViewById(R.id.etNombre)
        etCategoria = findViewById(R.id.etCategoria)
        etPrecio = findViewById(R.id.etPrecio)
        etStock = findViewById(R.id.etStock)
        btnSeleccionarMiniatura = findViewById(R.id.btnSeleccionarMiniatura)
        btnSubirImagenes = findViewById(R.id.btnSubirImagenes)
        btnGuardarProducto = findViewById(R.id.btnGuardarProducto)
        ivMiniatura = findViewById(R.id.ivMiniatura)
        rvImagenes = findViewById(R.id.rvImagenes)

        // Configurar RecyclerView para mostrar las imágenes seleccionadas
        adapter = ImageAdapter(imagenesUrls)
        rvImagenes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvImagenes.adapter = adapter
    }

    private fun seleccionarMiniatura() {
        selectMiniaturaLauncher.launch("image/*")
    }

    private fun seleccionarImagenes() {
        selectImagesLauncher.launch("image/*")
    }

    private fun guardarProducto() {
        val nombre = etNombre.text.toString().trim()
        val categoria = etCategoria.text.toString().trim()
        val precio = etPrecio.text.toString().trim()
        val stock = etStock.text.toString().toIntOrNull() ?: 0

        if (nombre.isEmpty() || categoria.isEmpty() || precio.isEmpty() || stock <= 0 || miniaturaUrl == null) {
            Toast.makeText(this, "Por favor, completa todos los campos y selecciona imágenes", Toast.LENGTH_LONG).show()
            return
        }

        val dbRef = FirebaseDatabase.getInstance().getReference("Productos")
        val productoId = dbRef.push().key ?: ""

        // Subir el producto con las URLs de las imágenes
        val producto = Product(
            productoId,
            nombre,
            categoria,
            precio,
            stock,
            miniaturaUrl ?: "",  // Usar la URL de la miniatura
            imagenesUrls  // Usar las URLs de las imágenes adicionales
        )

        // Guardar en la base de datos
        dbRef.child(productoId).setValue(producto).addOnSuccessListener {
            Toast.makeText(this, "Producto guardado exitosamente", Toast.LENGTH_LONG).show()
            finish()
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
}
