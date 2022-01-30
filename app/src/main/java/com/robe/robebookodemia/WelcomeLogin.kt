package com.robe.robebookodemia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.welcome_login.*
import com.robe.robebookodemia.tools.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import com.robe.robebookodemia.dataclass.*
import org.json.JSONObject
import java.lang.Thread.sleep

class WelcomeLogin : AppCompatActivity() {
    private var TAG = WelcomeLogin::class.qualifiedName
    private var parent_view: View? = null
    private var errorEmail = true
    private var errorPass = true

    private lateinit var til_correo: TextInputLayout
    private lateinit var tiet_correo: TextInputEditText
    private lateinit var til_contrasena: TextInputLayout
    private lateinit var tiet_contrasena: TextInputEditText
    private lateinit var btn_ingresar: Button
    private lateinit var pb_login: ProgressBar
    private lateinit var btnNewRegister: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_login)

        parent_view = findViewById(android.R.id.content)

        errorEmail = errorChecker(applicationContext, tiet_login_email, til_login_email, isEmail = true)
        errorPass = errorChecker(applicationContext, tiet_login_pass, til_login_pass, isEmail = false)

        /*btnLogin.setOnClickListener {
            if (inputsCorrect()) {
                startActivity(Intent(this, HomeBooks::class.java))
            }
        }*/



        init()

    }
    private fun init(){
        til_correo = findViewById(R.id.til_login_email)
        tiet_correo = findViewById(R.id.tiet_login_email)
        til_contrasena = findViewById(R.id.til_login_pass)
        tiet_contrasena = findViewById(R.id.tiet_login_pass)
        btn_ingresar = findViewById(R.id.btnLogin)
        pb_login = findViewById(R.id.pb_login)

        btn_ingresar.setOnClickListener{
            if(inputsCorrect()){
                realizarPeticion()
            }
        }
        btnNewRegister = findViewById(R.id.btnNewRegister)
        btnNewRegister.setOnClickListener {
            startActivity(Intent(this, NewAccount::class.java))
        }
    }
    private fun realizarPeticion(){
        VolleyLog.DEBUG = true
        if(isOnline(applicationContext)){
            btn_ingresar.visibility = View.GONE
            pb_login.visibility = View.VISIBLE
            val cola = Volley.newRequestQueue(applicationContext)
            val json = JSONObject()
            json.put("email",tiet_correo.text.toString())
            json.put("password",tiet_contrasena.text.toString())
            json.put("device_name","User's phone")
            val peticion = object: JsonObjectRequest(
                Request.Method.POST,getString(R.string.url_server)+getString(R.string.api_login),json,
                {
                        response ->
                    val jsonObject = JSONObject(response.toString())
                    initSesion(applicationContext,jsonObject)
                        sleep(1000)
                    if(validarSesion(applicationContext)){
                        startActivity(Intent(this, HomeBooks::class.java))
                        finish()
                    }
                },
                { error ->
                    btn_ingresar.visibility = View.VISIBLE
                    pb_login.visibility = View.GONE
                    val json = JSONObject(String(error.networkResponse.data, Charsets.UTF_8))
                    val errors = Json.decodeFromString<Errors>(json.toString())
                    for (error in errors.errors){
                        mensajeEmergente(this,error.detail)
                    }
                    Log.e(TAG,error.networkResponse.toString())
                    Log.e(TAG,error.toString())
                }
            ){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String,String>()
                    headers["Accept"] = "application/json"
                    headers["Content-type"] = "application/json"
                    return headers
                }
            }
            cola.add(peticion)
        }
        else{
            mensajeEmergente(this,getString(R.string.error_internet))
        }
    }

    private fun inputsCorrect(): Boolean {
        var valid = false
        // Force to run the validation-> paint the TIL
        var eEmail = anyErrorMail(applicationContext, tiet_login_email, til_login_email)
        var ePass = anyErrorPass(applicationContext, tiet_login_pass, til_login_pass)
        if (!eEmail && !ePass) {
            valid = true
        }
        return valid
    }

}