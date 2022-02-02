package com.robe.robebookodemia.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.card.MaterialCardView
import com.robe.robebookodemia.DetailBooks
import com.robe.robebookodemia.R
import com.robe.robebookodemia.dataclass.Book
import com.robe.robebookodemia.model.authors.AuthorsAll
import com.robe.robebookodemia.model.categories.CategoriesAll
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

//import android.content.Context
//import kotlinx.android.synthetic.main.dialog_agregar_libros.view.*
//import mx.kodemia.ejerciciolibrosrecycler.Libro
//import mx.kodemia.ejerciciolibrosrecycler.R

class AdapterLibro(val activity: Activity, val books: MutableList<Book>): RecyclerView.Adapter<AdapterLibro.BookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview_book,parent,false)
        return BookHolder(view)
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val book = books.get(position)
        with(holder){
            tv_title.setOnClickListener{
                val bundle = Bundle()
                bundle.putSerializable("book",book)
                val intent = Intent(activity,DetailBooks::class.java)
                intent.putExtras(bundle)
                activity.startActivity(intent)
            }
            tv_title.text = book.attributes.title
//            tv_author.text = book.relationships.toString()
//            tv_cat.text = book.relationships.categories.toString()
        }
    }


    override fun getItemCount(): Int = books.size


    class BookHolder(val view: View): RecyclerView.ViewHolder(view){
        val tv_title: TextView = view.findViewById(R.id.textView_name_book)
        val tv_author: TextView = view.findViewById(R.id.textView_author_book)
        val tv_cat: TextView = view.findViewById(R.id.textView_categ_book)
        private val AUTOR = "author"
        private val CATEGORY = "category"

//        val cv: MaterialCardView = view.findViewById(R.id.cardView_recien_agregados)
//        val img: ImageView = view.findViewById(R.id.image_recycler_agregados_portada)
//        val titulo: TextView = view.findViewById(R.id.text_recycler_agregados_titulo)
//        val autor: TextView = view.findViewById(R.id.text_recycler_agregados_autor)
//        val categoria: TextView = view.findViewById(R.id.text_recycler_agregados_categoria)
//        val btn_detalles: Button = view.findViewById(R.id.button_recycler_home_detalles)

        fun getRequests(libro: Book){
            getCategoriesOrAuthorsByRequest(libro.relationships.authors.links.related, tv_author, AUTOR)
            getCategoriesOrAuthorsByRequest(libro.relationships.categories.links.related, tv_cat, CATEGORY)
        }

        fun setInfo(libro: Book) {
            //Glide.with(view).load(libro.img).diskCacheStrategy(DiskCacheStrategy.NONE).into(img)

            getRequests(libro)
//            img.setImageResource(R.drawable.libro_1)
//            titulo.text = libro.attributes.title

        }

        private fun getCategoriesOrAuthorsByRequest(url: String, textView: TextView, type: String) {
            val queue = Volley.newRequestQueue(view.context)

            val request = object : JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                { response ->
                    if(type == CATEGORY) {
                        val r = Json.decodeFromString<CategoriesAll>(response.toString())
                        textView.text = r.data.attributes.name
                    }
                    else{
                        val r = Json.decodeFromString<AuthorsAll>(response.toString())
                        textView.text = view.context.getString(R.string.by_author, r.data.attributes.name)
                    }
                },
                { error ->
                    Log.e("Recycler", error.toString())
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    headers["Content-Type"] = "application/json"
                    return headers
                }
            }
            queue.add(request)
        }
    }
}