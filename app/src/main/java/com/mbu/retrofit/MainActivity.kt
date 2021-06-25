package com.mbu.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import com.mbu.retrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    //Adater RV
    private lateinit var adaptador: PerroAdapter
    private val perrosImagenes = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.buscadorTop.setOnQueryTextListener(this)
        val search = findViewById<SearchView>(R.id.buscador_top)
        search.setOnQueryTextListener(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adaptador = PerroAdapter(perrosImagenes)
        binding.miRv.layoutManager = LinearLayoutManager(this)
        binding.miRv.adapter = adaptador

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByName(query: String) {
        //todo lo de aqui se hara en un hilo secundario
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getDogByBreeds("$query/images")
            println("====cccc=====")
            println(call)
            val puppies: PerrosModel? = call.body()
            //Todo dentro de runONUITHread se ejecutara en el hilo principal -
            runOnUiThread {
                //aqui pinta el adpater o el toast
                if (call.isSuccessful) {
                    //mostrar RcyclerVoew
                    val images: List<String> =puppies?.images ?: emptyList()
                    perrosImagenes.clear()
                    perrosImagenes.addAll(images)
                    println("=========")
                    println(perrosImagenes)
                    adaptador.notifyDataSetChanged()
                } else {
                    //Mostrar error
                    showError()
                }
                hideKeyboard()
            }



        }

    }

    private fun showError(){
        Toast.makeText(this, "Ocurrio un error", Toast.LENGTH_LONG).show()
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //viewRoot es el id principal del activity_main.xml
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchByName(query.lowercase(Locale.getDefault()))
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }



}