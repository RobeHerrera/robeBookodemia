package com.robe.robebookodemia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.robe.robebookodemia.R
import com.robe.robebookodemia.dataclass.Book
import com.robe.robebookodemia.model.Libro


class AdapterPopulares(val listLibros: MutableList<Book>):
    RecyclerView.Adapter<AdapterPopulares.LibroHolder>() {
    class LibroHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val cl : ConstraintLayout = view.findViewById(R.id.cl_populares)
        val img: ImageView = view.findViewById(R.id.imagen_libro_populares)

        fun setImagen(libro: Book){
            //Glide.with(view).load(libro.url_img).diskCacheStrategy(DiskCacheStrategy.NONE).into(img)
            with(img){
                if(position % 2 == 0)
                    setImageResource(R.drawable.libro_2)
                else
                    setImageResource(R.drawable.libro_1)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LibroHolder(layoutInflater.inflate(R.layout.layout_mas_populares, parent, false))
    }

    override fun onBindViewHolder(holder: LibroHolder, position: Int) {
        holder.setImagen(listLibros[position])
    }

    override fun getItemCount(): Int = listLibros.size


}