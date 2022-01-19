package com.robe.robebookodemia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.robe.robebookodemia.adapter.AdapterLibro
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
        listLibros.add(Libro("https://cloudfront-eu-central-1.images.arcpublishing.com/prisa/2FEZLOAZVN2WNSJRJPOGCA2EOY.jpg"))
        recyclerview_libros.layoutManager = LinearLayoutManager(this)
        recyclerview_libros.setHasFixedSize(true)
        adapterLibro = AdapterLibro(listLibros)
        recyclerview_libros.adapter = adapterLibro
    }
}