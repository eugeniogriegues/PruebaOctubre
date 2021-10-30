package com.example.pruebaoctubre

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class PhotoList : AppCompatActivity() {

   /* val listaParaTestearPhoto = listOf (    //  LISTA DE ITEMS PARA TESTEAR EL RECYCLERVIEW Y LA CONECTIVIDAD
        Photo (1, 1, "accusamus beatae ad facilis cum similique qui sunt","https://via.placeholder.com/600/92c952","https://via.placeholder.com/150/92c952"),
        Photo (1, 2, "reprehenderit est deserunt velit ipsam","https://via.placeholder.com/600/771796","https://via.placeholder.com/150/771796"),
        Photo (1, 3, "officia porro iure quia iusto qui ipsa ut modi","https://via.placeholder.com/600/24f355","https://via.placeholder.com/150/24f355"),
        Photo (1, 4, "culpa odio esse rerum omnis laboriosam voluptate repudiandae","https://via.placeholder.com/600/d32776","https://via.placeholder.com/150/d32776"),
        Photo (1, 5, "natus nisi omnis corporis facere molestiae rerum in","https://via.placeholder.com/600/f66b97","https://via.placeholder.com/150/f66b97"),
        Photo (1, 6, "accusamus ea aliquid et amet sequi nemo","https://via.placeholder.com/600/56a8c2","https://via.placeholder.com/150/56a8c2"),
        Photo (1, 7, "officia delectus consequatur vero aut veniam explicabo molestias","https://via.placeholder.com/600/b0f7cc","https://via.placeholder.com/150/b0f7cc"),
        Photo (1, 8, "aut porro officiis laborum odit ea laudantium corporis","https://via.placeholder.com/600/54176f","https://via.placeholder.com/150/54176f"),
        Photo (1, 9, "qui eius qui autem sed","https://via.placeholder.com/600/51aa97","https://via.placeholder.com/150/51aa97"),
        Photo (1, 10, "beatae et provident et ut vel","https://via.placeholder.com/600/810b14","https://via.placeholder.com/150/810b14"),


    )  */



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_list)


        val extras = intent.extras

        val idRecuperado = extras?.getInt("id").toString()


        obtenerListaPhoto(idRecuperado)


    }


    private fun iniciarRecyclerPhoto (listaPhoto : List<Photo>) {  //  METODO PARA INICIAR EL RECYCLERVIEW CON UNA LISTA DE OBJETOS PHOTO

        val adapterPhoto = AdapterPhotoList(listaPhoto)

        val recyclerViewPhoto = findViewById<RecyclerView>(R.id.rvListadoPhotos)

        recyclerViewPhoto?.layoutManager =
            StaggeredGridLayoutManager(
                1,
                StaggeredGridLayoutManager.VERTICAL
            )

        recyclerViewPhoto?.adapter = adapterPhoto

    }



    private fun obtenerListaPhoto (inputId: String)  {


        CoroutineScope(Dispatchers.IO).launch {

            val client = ApiClient.apiService.getPhotos("/albums/"+inputId+"/photos")

            client.enqueue(object : retrofit2.Callback<List<Photo>> {

                override fun onResponse(
                    call: Call<List<Photo>>,
                    response: Response<List<Photo>>
                )

                {
                    if (response.isSuccessful) {


                        val resultadosPhoto = response.body()

                        resultadosPhoto?.let {


                            iniciarRecyclerPhoto(resultadosPhoto)   // ACTUALIZA LA LISTA CON LOS NUEVOS RESULTADOS

                        }

                    }

                }

                override fun onFailure(call: Call<List<Photo>>, t: Throwable) {

                    mostrarMensaje("ERROR AL OBTENER LAS FOTOS")


                }

            })

        }

    }

    private fun mostrarMensaje (x: String) {

        Toast.makeText(this, x, Toast.LENGTH_LONG).show()

    }

}