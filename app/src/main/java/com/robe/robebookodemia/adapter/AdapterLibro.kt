package com.robe.robebookodemia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.card.MaterialCardView
import com.robe.robebookodemia.Libro
import com.robe.robebookodemia.R
//import kotlinx.android.synthetic.main.dialog_agregar_libros.view.*
//import mx.kodemia.ejerciciolibrosrecycler.Libro
//import mx.kodemia.ejerciciolibrosrecycler.R

class AdapterLibro(val libros: MutableList<Libro>) :
    RecyclerView.Adapter<AdapterLibro.LibroHolder>() {

    class LibroHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val cardView : MaterialCardView = view.findViewById(R.id.cardView_item_book)
        val imagenView : ImageView = view.findViewById(R.id.imageView_book)

        fun render(imagen: Libro){
            Glide.with(view).load(imagen.url).diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imagenView)
            cardView.setOnClickListener {
//                Snackbar.make(view, "Tap en ${koder.nombre}", Snackbar.LENGTH_SHORT).show()
                //showDialog(view.context, koder)
                println("Se va a llamar una activity aqui........")
            }
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

    fun agregarLibro(libro: Libro){
        this.libros.add(libro)
        notifyItemInserted(itemCount)
    }
}