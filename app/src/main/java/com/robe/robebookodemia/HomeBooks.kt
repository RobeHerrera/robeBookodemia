package com.robe.robebookodemia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.robe.robebookodemia.adapter.AdapterLibro
import com.robe.robebookodemia.adapter.AdapterPopulares
import com.robe.robebookodemia.dataclass.Book
import com.robe.robebookodemia.dataclass.Books
import com.robe.robebookodemia.dataclass.Errors
import com.robe.robebookodemia.tools.eliminarSesion
import com.robe.robebookodemia.tools.isOnline
import com.robe.robebookodemia.tools.mensajeEmergente
import com.robe.robebookodemia.tools.obtainToken
import kotlinx.android.synthetic.main.activity_home_books.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class HomeBooks : AppCompatActivity() {

    /* No me hac emucho sentido estas varibales globales aqui...*/
    val listLibros: MutableList<Book> = mutableListOf()

    //    var adapterLibro = AdapterLibro(this, listLibros)
    var adapterPopulares = AdapterPopulares(listLibros)
    private val TAG = HomeBooks::class.qualifiedName

    //    private lateinit var btn_cerrar_sesion: Button
    private lateinit var rv_books: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_books)

        init()
        initRecyclerLibros(listLibros)
        initCarouselPopulares()
        initBottomNavigation()

    }

    private fun initBottomNavigation() {
        bottomNavigationView2.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuUser -> {
                    startActivity(Intent(this, User::class.java))
                    true
                }
                /*R.id.menuHome -> {
                    setCurrentFragment(homeFragment)
                    true
                }
                R.id.menuBooks -> {
                    setCurrentFragment(userFragment)
                    true
                }*/
                else -> false
            }
        }
    }

    private fun initCarouselPopulares() {
        recycler_populares.adapter = adapterPopulares
        recycler_populares.setInfinite(true)
        recycler_populares.setIntervalRatio(0.6f)
    }

    fun initRecyclerLibros(listBooks: MutableList<Book>) {
//        listLibros.add(Libro("El señor de los anillos","J.R.R. Tolkien", "Novel","https://static.wikia.nocookie.net/eldragonverde/images/b/b2/Cinemania_lordoftherings.jpg/revision/latest/smart/width/250/height/250?cb=20130103162924&path-prefix=es"))
//        listLibros.add(Libro("El hobbit","J.R.R. Tolkien", "Novel","https://www.lifeder.com/wp-content/uploads/2017/09/Guadalajara-Jalisco-min-696x464.jpg"))
//        listLibros.add(Libro("Lord of Rings","J.R.R. Tolkien", "Novel","https://www.prensalibre.com/wp-content/uploads/2020/03/El-sen%CC%83or-de-los-anillos-Golum-.jpg?quality=52"))
//        listLibros.add(Libro("Lord de los anillos 2","J.R.R. Tolkien", "Novel","https://deviramericas.com/wp-content/uploads/2021/08/El-Senor-de-los-Anillos-20-aniv-600x600-1.jpg"))
//        recyclerview_libros.layoutManager = LinearLayoutManager(this)
//        recyclerview_libros.setHasFixedSize(true)
//        recyclerview_libros.adapter = adapterLibro

        //val adapterAgregados = AdapterLibro(listBooks)
        var adapterLibro = AdapterLibro(this, listLibros)
        recyclerview_libros.layoutManager = LinearLayoutManager(this)
        recyclerview_libros.adapter = adapterLibro
        recyclerview_libros.setHasFixedSize(true)


    }

    fun goBookDetails(view: View) {
        startActivity(Intent(this, DetailBooks::class.java))
    }

    fun init() {
        rv_books = findViewById(R.id.recyclerview_libros)
    }

    override fun onResume() {
        super.onResume()
        realizarPeticion()
    }

    fun realizarPeticion() {
        if (isOnline(applicationContext)) {
            val cola = Volley.newRequestQueue(applicationContext)
            val peticion = object : JsonObjectRequest(
                Request.Method.GET,
                getString(R.string.url_server) + getString(R.string.api_libros),
                null,
                { response ->
                    Log.d(TAG, response.toString())
                    val books = Json.decodeFromString<Books>(response.toString())
                    val adaptador = AdapterLibro(this, books.data)
                    rv_books.layoutManager = LinearLayoutManager(this)
                    rv_books.adapter = adaptador
                    adaptador.notifyDataSetChanged()
                    rv_books.visibility = View.VISIBLE
                },
                { error ->
                    if (error.networkResponse.statusCode == 401) {
                        eliminarSesion(applicationContext)
                        val intent = Intent(this, WelcomeLogin::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    } else {
                        val json = JSONObject(String(error.networkResponse.data, Charsets.UTF_8))
                        val errors = Json.decodeFromString<Errors>(json.toString())
                        for (error in errors.errors) {
                            mensajeEmergente(this, error.detail)
                        }
                        rv_books.visibility = View.GONE
                    }
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] =
                        "Bearer ${obtainToken(applicationContext, "token")}"
                    headers["Accept"] = "application/json"
                    headers["Content-type"] = "application/json"
                    return headers
                }
            }
            cola.add(peticion)
        } else {
            mensajeEmergente(this, getString(R.string.error_internet))
        }
    }
}