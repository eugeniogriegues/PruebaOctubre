package com.example.pruebaoctubre

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter (private val albumList: List<Album>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    inner class MainViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {          // TODO ****  listener para cada item del recyclerView ****

             itemView.setOnClickListener { v: View ->


                val intent = Intent (v.context.applicationContext, PhotoList::class.java)


                intent.putExtra("id", albumList[adapterPosition].id)


                v.context.startActivity(intent) // todo INICIA EL ACTICITY DETALLE AL HACER TAP EN ALGUN ELEMENTO DE LA LISTA


            }

        }

            fun bindData(album: Album) {

                val id = itemView.findViewById<TextView>(R.id.tvId)
                val title = itemView.findViewById<TextView>(R.id.tvNombreAlbum)


                id.text = "ID= " + album.id
                title.text = album.title


            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

            return MainViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

            holder.bindData(albumList[position])
        }

        override fun getItemCount(): Int {

            return albumList.size
        }



}






