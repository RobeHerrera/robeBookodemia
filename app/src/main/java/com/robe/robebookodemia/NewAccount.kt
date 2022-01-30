package com.robe.robebookodemia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.robe.robebookodemia.tools.*
import com.robe.robebookodemia.dataclass.Errors
import kotlinx.android.synthetic.main.activity_new_account.*
import kotlinx.android.synthetic.main.welcome_login.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class NewAccount : AppCompatActivity() {

    private val TAG = NewAccount::class.qualifiedName
    private lateinit var til_registro_nombre: TextInputLayout
    private lateinit var tiet_registro_nombre: TextInputEditText
    private lateinit var til_registro_correo: TextInputLayout
    private lateinit var tiet_registro_correo: TextInputEditText
    private lateinit var til_registro_contrasena: TextInputLayout
    private lateinit var tiet_registro_contrasena: TextInputEditText
    private lateinit var til_registro_contrasena_dos: TextInputLayout
    private lateinit var tiet_registro_contrasena_dos: TextInputEditText
    private lateinit var btn_registrar: Button

    var eUser = true
    var eEmail = true
    var ePass1 = true
    var ePass2 = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account)

        eUser = errorChecker(applicationContext, tiet_reg_user, til_reg_user, isEmail = false)
        eEmail = errorChecker(applicationContext, tiet_reg_email, til_reg_email, isEmail = true)
        ePass1 = errorChecker(applicationContext, tiet_reg_pass1, til_reg_pass1, isEmail = false)
        ePass2 = errorChecker(applicationContext, tiet_reg_pass2, til_reg_pass2, isEmail = false)

        init()

        btnBackRegister.setOnClickListener {
            finish()
        }
    }

    fun init() {
        til_registro_nombre = findViewById(R.id.til_reg_user)
        tiet_registro_nombre = findViewById(R.id.tiet_reg_user)
        til_registro_correo = findViewById(R.id.til_reg_email)
        tiet_registro_correo = findViewById(R.id.tiet_reg_email)
        til_registro_contrasena = findViewById(R.id.til_reg_pass1)
        tiet_registro_contrasena = findViewById(R.id.tiet_reg_pass1)
        til_registro_contrasena_dos = findViewById(R.id.til_reg_pass2)
        tiet_registro_contrasena_dos = findViewById(R.id.tiet_reg_pass2)

        btn_registrar = findViewById(R.id.btn_new_register)
        btn_registrar.setOnClickListener {
            if (inputsCorrect()) {
                realizarPeticion()
            }
        }
    }

    private fun inputsCorrect(): Boolean {
        var valid = false


        if (!tiet_registro_contrasena.text.toString()
                .equals(tiet_registro_contrasena_dos.text.toString())
        ) {
            Toast.makeText(
                this,
                applicationContext.getString(R.string.error_different_pass),
                Toast.LENGTH_SHORT
          ).show()
            til_registro_contrasena.error =
                  applicationContext.getString(R.string.error_different_pass)
            til_registro_contrasena_dos.error =
                applicationContext.getString(R.string.error_different_pass)
            valid = false
        } else {
            til_registro_contrasena_dos.isErrorEnabled = false
            valid = true
        }
        // Force to run the validation-> paint the TIL
        var e1 = anyErrorPass(applicationContext, tiet_reg_user, til_reg_user)
        var e2 = anyErrorMail(applicationContext, tiet_reg_email, til_reg_email)
        var e3 = anyErrorPass(applicationContext, tiet_reg_pass1, til_reg_pass1)
        var e4 = anyErrorPass(applicationContext, tiet_reg_pass2, til_reg_pass2)
        if (e1 || e2 || e3 || e4) {
            valid = false
        }

        return valid
    }

    private fun realizarPeticion() {
        if (isOnline(applicationContext)) {
            val json = JSONObject()
            json.put("name", tiet_registro_nombre.text)
            json.put("email", tiet_registro_correo.text)
            json.put("password", tiet_registro_contrasena.text)
            json.put("password_confirmation", tiet_registro_contrasena_dos.text)
            json.put("device_name", "User's phone")
            val cola = Volley.newRequestQueue(applicationContext)
            val peticion = object : JsonObjectRequest(
                Method.POST,
                getString(R.string.url_server) + getString(R.string.api_registro),
                json,
                { response ->
                    Log.d(TAG, response.toString())
                    val jsonObject = JSONObject(response.toString())
                    initSesion(applicationContext, jsonObject)
                    val intent = Intent(this, WelcomeLogin::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    mensajeEmergente(this, "Cuenta Creada")
                    finish()
                },
                { error ->
                    val json = JSONObject(String(error.networkResponse.data, Charsets.UTF_8))
                    val errors = Json.decodeFromString<Errors>(json.toString())
                    for (error in errors.errors) {
                        mensajeEmergente(this, error.detail)
                    }
                    Log.e(TAG, error.toString())
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    headers["Content-type"] = "application/json"
                    return headers
                }
            }
            cola.add(peticion)
        } else {
            mensajeEmergente(this, getString(R.string.error_internet))
        }
    }
}