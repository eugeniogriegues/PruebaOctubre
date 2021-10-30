package com.example.pruebaoctubre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pruebaoctubre.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {


        var respuestaLista : List<Album>? = null


     /*  val listaParaTestear = listOf (    //  LISTA DE ITEMS PARA TESTEAR EL RECYCLERVIEW Y LA CONECTIVIDAD
        Album (1, 1, "quidem molestiae enim"),
           Album (1, 2, "sunt qui excepturi placeat culpa"),
           Album (1, 3, "omnis laborum odio"),
           Album (1, 4, "non esse culpa molestiae omnis sed optio",),
           Album (1, 5, "eaque aut omnis a", ),
           Album (1, 6, "natus impedit quibusdam illo est"),
           Album (1, 7, "quibusdam autem aliquid et et quia"),
           Album (1, 8, "qui fuga est a eum"),
           Album (1, 9, "saepe unde necessitatibus rem"),
           Album (1, 10, "distinctio laborum qui"),
           Album (2, 11, "quidem molestiae enim"),
           Album (2, 12, "quidem molestiae enim"),
           Album (2, 13, "quidem molestiae enim"),
           Album (2, 14, "quidem molestiae enim"),
           Album (2, 15, "quidem molestiae enim"),
           Album (2, 16, "quidem molestiae enim"),
           Album (2, 17, "quidem molestiae enim"),
           Album (2, 18, "quidem molestiae enim"),
           Album (2, 19, "quidem molestiae enim"),
           Album (2, 20, "quidem molestiae enim")
    )  */




    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //iniciarRecycler(listaParaTestear)

        obtenerListaAlbum()



        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(inputTexto: String): Boolean {

                binding.searchView.clearFocus()

                for (i in 0..99) {

                   if (inputTexto == respuestaLista?.get(i)?.title  ) {

                       mostrarMensaje("ALBUM ENCONTRADO !!!")



                       val intent = Intent (applicationContext, PhotoList::class.java)


                       intent.putExtra("id", respuestaLista?.get(i)?.id)


                       startActivity(intent)

                       break

                   } else {

                       mostrarMensaje("ALBUM NO ENCONTRADO")

                   }

                }


                return false

            }

            override fun onQueryTextChange(inputTexto: String): Boolean {

                return false
            }

        })

    }



    private fun iniciarRecycler (lista : List<Album>) {  //  METODO PARA INICIAR EL RECYCLERVIEW CON UNA LISTA DE ALBUMS

        val adapter = MainAdapter(lista)

        val recyclerView = findViewById<RecyclerView>(R.id.rvListadoItems)

        recyclerView?.layoutManager =
            StaggeredGridLayoutManager(
                1,
                StaggeredGridLayoutManager.VERTICAL
            )

        recyclerView?.adapter = adapter

    }





    private fun obtenerListaAlbum ()  {


        CoroutineScope(Dispatchers.IO).launch {

            val client = ApiClient.apiService.getAlbumList()

            client.enqueue(object : retrofit2.Callback<List<Album>> {

                override fun onResponse(
                    call: Call<List<Album>>,
                    response: Response<List<Album>>
                )

                {
                    if (response.isSuccessful) {

                        mostrarMensaje("DESCARGA EXITOSA !!!")

                        val resultados = response.body()

                        resultados.also { respuestaLista = it }  // GUARDO LA LISTA DE ALBUMS PARA LUEGO EFECTUAR BUSQUEDAS

                        resultados?.let {


                            iniciarRecycler(resultados)   // ACTUALIZA LA LISTA CON LOS NUEVOS RESULTADOS

                        }

                    }

                }

                override fun onFailure(call: Call<List<Album>>, t: Throwable) {

                        mostrarMensaje("ERROR AL OBTENER LISTA")


                }

            })

        }

    }

    private fun mostrarMensaje (x: String) {

        Toast.makeText(this, x, Toast.LENGTH_LONG).show()

    }


}