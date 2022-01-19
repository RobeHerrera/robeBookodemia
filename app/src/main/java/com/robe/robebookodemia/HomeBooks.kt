package com.robe.robebookodemia

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.robe.robebookodemia.adapter.AdapterLibro
import com.robe.robebookodemia.model.Libro
import kotlinx.android.synthetic.main.activity_home_books.*

class HomeBooks : AppCompatActivity() {

    /* No me hac emucho sentido estas varibales globales aqui...*/
    val listLibros : MutableList<Libro> = mutableListOf()
    var adapterLibro = AdapterLibro(listLibros)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_books)

        initRecyclerLibros()

    }

    fun initRecyclerLibros(){
        listLibros.add(Libro("El señor de los anillos","J.R.R. Tolkien", "Novel","https://static.wikia.nocookie.net/eldragonverde/images/b/b2/Cinemania_lordoftherings.jpg/revision/latest/smart/width/250/height/250?cb=20130103162924&path-prefix=es"))
        listLibros.add(Libro("El hobbit","J.R.R. Tolkien", "Novel","https://www.lifeder.com/wp-content/uploads/2017/09/Guadalajara-Jalisco-min-696x464.jpg"))
        listLibros.add(Libro("Lord of Rings","J.R.R. Tolkien", "Novel","https://www.prensalibre.com/wp-content/uploads/2020/03/El-sen%CC%83or-de-los-anillos-Golum-.jpg?quality=52"))
        listLibros.add(Libro("Lord de los anillos 2","J.R.R. Tolkien", "Novel","https://deviramericas.com/wp-content/uploads/2021/08/El-Senor-de-los-Anillos-20-aniv-600x600-1.jpg"))
        recyclerview_libros.layoutManager = LinearLayoutManager(this)
        recyclerview_libros.setHasFixedSize(true)
        adapterLibro = AdapterLibro(listLibros)
        recyclerview_libros.adapter = adapterLibro
    }

    fun goBookDetails(view: View){
        val intent = Intent(this, DetailBooks::class.java)
        startActivity(intent)
    }
}