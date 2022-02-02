package com.robe.robebookodemia.tools

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.robe.robebookodemia.R
import android.content.SharedPreferences
import android.os.Build
import android.view.Gravity
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject


fun errorChecker(
    context: Context,
    tiet: TextInputEditText,
    til: TextInputLayout,
    isEmail: Boolean
): Boolean {
    // Is there any error
    var error = true
    tiet.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(editText: Editable?) {

            /* Checker for normal */
                error = anyErrorPass(context,tiet,til)
            /* Checker for EMAIL */
            if (isEmail) {
               error = anyErrorMail(context,tiet,til)
            }
        }
    })
    return error
}

fun anyErrorMail(
    context: Context,
    tiet: TextInputEditText,
    til: TextInputLayout,
): Boolean {
    var error: Boolean
    if (android.util.Patterns.EMAIL_ADDRESS.matcher(tiet.text.toString())
            .matches()
    ) {
        til.isErrorEnabled = false
        error = false
    } else {
        til.error = context.getString(R.string.error_invalid_email)
        error = true
    }
    return error
}

fun eliminarSesion(context: Context){
    val sharedPreferences = obtenerPreferencias(context)
    with(sharedPreferences.edit()){
        clear()
        apply()
    }
}
fun obtainToken(context: Context,clave: String): String{
    val sharedPreferences = obtenerPreferencias(context)
    return sharedPreferences.getString(clave,"").toString()
}
fun anyErrorPass(
    context: Context,
    tiet: TextInputEditText,
    til: TextInputLayout,
    ): Boolean {
    var error:Boolean
    if (tiet.text.toString().trim().isEmpty()) {
        til.error = context.getString(R.string.error_empty)
        error = true
    } else {
        til.isErrorEnabled = false
        error = false
    }
    return error
}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
        }
    }
    return false
}//end of function estaEnLinea

fun initSesion(context: Context, jsonObject: JSONObject){
    val sharedPreferences = obtenerPreferencias(context)
    //Se puede obtener de la siguiente manera
    // jsonObject.getString("token")
    // jsonObject.getString(context.getString(R.string.key_token))
    // jsonObject["token"].toString()
    // jsonObject[context.getString(R.string.key_token)].toString()
    with(sharedPreferences.edit()){
        putString("token",jsonObject.getString(context.getString(R.string.key_token)))
        apply()
    }
}
fun obtenerPreferencias(context: Context): SharedPreferences{
    return EncryptedSharedPreferences.create(context.getString(
        R.string.name_file_preferences),
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

fun mensajeEmergente(
    activity: Activity,
    mensaje: String
){
    val snackBar = Snackbar.make(activity.findViewById(android.R.id.content),mensaje, Snackbar.LENGTH_LONG)
    val view = snackBar.view
    val params = snackBar.view.layoutParams as FrameLayout.LayoutParams
    params.gravity = Gravity.TOP
    view.layoutParams = params
    snackBar.show()
}//end of function mensajeEmergente

fun validarSesion(context: Context): Boolean{
    val sharedPreferences = obtenerPreferencias(context)
    val token = sharedPreferences.getString("token","")
    println("token: $token") //TODO: erase this print token
    return !token.isNullOrEmpty()
}


