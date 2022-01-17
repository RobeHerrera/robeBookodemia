package com.robe.robebookodemia

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_login)

        var btnNewRegister:Button = findViewById(R.id.btnNewRegister)
        btnNewRegister.setOnClickListener {

//            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, NewRegister.newInstance())
                    .commitNow()
//            }
        }

    }

}