package com.example.listadoprod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadoprod.databinding.ActivityMainBinding
import dataadapter.ProductoAdapter
import dataclass.Producto

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var listaProd = ArrayList<Producto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciar()
    }

    private fun limpiar(){
        with(binding){
            etID.setText("")
            etNombreP.setText("")
            etPrecio.setText("")
            etID.requestFocus()
        }
    }

    private fun agregarprod(){
        with(binding){
            try {
                val id: Int = etID.text.toString().toInt()
                val nombre :String = etNombreP.text.toString()
                val precio: Double= etPrecio.text.toString().toDouble()
                val prod = Producto(id,nombre,precio)
                listaProd.add(prod)
            }catch (ex: Exception){
                Toast.makeText(this@MainActivity, "Error:${ex.toString()}",
                Toast.LENGTH_LONG).show()
            }
            rcvLista.layoutManager= LinearLayoutManager(this@MainActivity)
            rcvLista.adapter=ProductoAdapter(listaProd)
            limpiar()
        }
    }

    private fun borarProd(){
        try {
            with(binding){
                val id =etID.text.toString().toInt()
                var valor = false
                var i=0

                while (i<listaProd.size && !valor){
                    if (listaProd[i].id==id){
                        listaProd.removeAt(i)
                        valor=true
                    }
                    i++
                }
                if (valor){
                    rcvLista.layoutManager= LinearLayoutManager(this@MainActivity)
                    rcvLista.adapter=ProductoAdapter(listaProd)
                }
            }
        }catch (ex:Exception){
            Toast.makeText(this@MainActivity,"Error al momento de borar ,berifique el si el valor es correcto",
                Toast.LENGTH_LONG).show()
        }
limpiar()
    }

    private fun editarProd(){
        try {
            with(binding){
                val id:Int=etID.text.toString().toInt()
                var pareja = false
                var i=0
                while (i<listaProd.size && !pareja){
                    if (listaProd[i].id==id){

                       listaProd[i].id  =etID.text.toString().toInt()
                       listaProd[i].nombre=etNombreP.text.toString()
                        listaProd[i].precio=etPrecio.text.toString().toDouble()
                        pareja =true


                    }
                    i++
                }
                if(pareja){
                    rcvLista.layoutManager=LinearLayoutManager(this@MainActivity)
                    rcvLista.adapter=ProductoAdapter(listaProd)

            }
            }

        }catch (ex: Exception){
            Toast.makeText(this@MainActivity,"verifique si los datos son correctos",
                Toast.LENGTH_SHORT).show()
        }
        limpiar()
    }


    private fun  iniciar(){
        binding.btnAgregar.setOnClickListener{
            agregarprod()
        }
        binding.btnLimpiar.setOnClickListener{
            limpiar()
        }
       binding.btnEliminar.setOnClickListener{
           AlertDialog.Builder(this)
               .setTitle("Eliminar")
               .setMessage("¿Decea eliminar este producto?")
               .setPositiveButton("si"){_,_->
                   borarProd()
               }
               .setNegativeButton("No"){ dialog,_->
                   dialog.dismiss()
                   Toast.makeText(this@MainActivity,"cancelado",
                       Toast.LENGTH_SHORT).show()
               }
               .show()
       }

        binding.btnEditar.setOnClickListener{
            editarProd()
        }
    }

}