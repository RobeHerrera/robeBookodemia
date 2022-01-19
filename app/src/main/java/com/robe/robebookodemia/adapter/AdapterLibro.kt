package com.robe.robebookodemia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.card.MaterialCardView
import com.robe.robebookodemia.R
import com.robe.robebookodemia.model.Libro

//import android.content.Context
//import kotlinx.android.synthetic.main.dialog_agregar_libros.view.*
//import mx.kodemia.ejerciciolibrosrecycler.Libro
//import mx.kodemia.ejerciciolibrosrecycler.R

class AdapterLibro(val libros: MutableList<Libro>) :
    RecyclerView.Adapter<AdapterLibro.LibroHolder>() {

    class LibroHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtViewBookName: TextView = view.findViewById(R.id.textView_name_book)
        val txtViewBookAuthor: TextView = view.findViewById(R.id.textView_author_book)
        val txtViewBookCateg: TextView = view.findViewById(R.id.textView_categ_book)
        val cardView: MaterialCardView = view.findViewById(R.id.cardView_item_book)
        val imagenView: ImageView = view.findViewById(R.id.imageView_book)

        fun render(book: Libro) {
            txtViewBookName.setText(book.name)
            txtViewBookAuthor.setText(book.author)
            txtViewBookCateg.setText(book.category)
            Glide.with(view).load(book.url_img).error(R.drawable.libro_1)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imagenView)


//            cardView.setOnClickListener {
//                val intent = Intent(@View:, HomeBooks::class.java)
//                startActivity(intent)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LibroHolder(layoutInflater.inflate(R.layout.item_cardview_book, parent, false))
    }

    override fun onBindViewHolder(holder: LibroHolder, position: Int) {
        holder.render(libros[position])
    }

    override fun getItemCount(): Int = libros.size

    fun agregarLibro(libro: Libro) {
        this.libros.add(libro)
        notifyItemInserted(itemCount)
    }
}