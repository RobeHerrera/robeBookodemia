package com.robe.robebookodemia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.robe.robebookodemia.tools.*

class User : AppCompatActivity() {

    private val TAG = User::class.qualifiedName
    private lateinit var btn_cerrar_sesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        initUser()

        var btnBackUser = findViewById<Button>(R.id.btnBackUser)
        btnBackUser.setOnClickListener {
            finish()
        }
    }

    private fun initUser() {
        btn_cerrar_sesion = findViewById(R.id.btn_close_sesion)
        btn_cerrar_sesion.setOnClickListener{
            val cola = Volley.newRequestQueue(applicationContext)
            val peticion = object: StringRequest(Request.Method.POST,getString(R.string.url_server)
                    +getString(R.string.api_logout),{
                    response ->
                Log.d(TAG,"Todo salio bien")
                eliminarSesion(applicationContext)
                startActivity(Intent(this,WelcomeLogin::class.java))
                finish()
            },{
                    error ->
                Log.e(TAG,error.toString())
            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String,String>()
                    headers["Authorization"] = "Bearer ${obtainToken(applicationContext,"token")}"
                    return headers
                }
            }
            cola.add(peticion)
        }
    }

}