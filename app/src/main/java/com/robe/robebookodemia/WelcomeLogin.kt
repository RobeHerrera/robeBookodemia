package com.robe.robebookodemia

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_login)

//        var btnNewRegister:Button = findViewById(R.id.btnBackNewRegister)
//        btnNewRegister.setOnClickListener {
//
//            if (savedInstanceState == null) {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, NewRegister.newInstance())
//                    .commitNow()
//            }
//        }


//        var btnBack:Button = findViewById(R.id.btnBackNewRegister)
//        btnBack.setOnClickListener {
//
//            if (savedInstanceState == null) {
//                supportFragmentManager.beginTransaction()
//                    .remove(NewRegister.newInstance())
//                    .commitNow()
//            }
//        }

    }

    /** Called when the user taps the Send button */
    fun newAccount(view: View) {
        val message = "Esto es un extra message"
        val intent = Intent(this, NewAccount::class.java)
        startActivity(intent)
    }

    fun myLogin(view: View) {
        val message = "Esto es un extra message"
        val intent = Intent(this, DetailBooks::class.java)
        startActivity(intent)
    }
}