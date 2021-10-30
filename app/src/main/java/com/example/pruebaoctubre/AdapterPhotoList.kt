package com.example.pruebaoctubre


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load

class AdapterPhotoList (private val photoList: List<Photo>) : RecyclerView.Adapter<AdapterPhotoList.MainViewHolder>() {

    inner class MainViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {          // TODO ****  listener para cada item del recyclerView ****

            itemView.setOnClickListener { v: View ->


                Toast.makeText(v.context, photoList[adapterPosition].id.toString(),Toast.LENGTH_LONG).show()


            }

        }

        fun bindData(photo: Photo) {

            val id = itemView.findViewById<TextView>(R.id.output_detail_id)
            val title = itemView.findViewById<TextView>(R.id.output_detail_title)
            val thumbnailUrl = itemView.findViewById<ImageView>(R.id.output_detail_imagen)


            id.text = "ID= " + photo.id
            title.text = photo.title
            thumbnailUrl.load(photo.thumbnailUrl)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        holder.bindData(photoList[position])
    }

    override fun getItemCount(): Int {

        return photoList.size
    }



}






