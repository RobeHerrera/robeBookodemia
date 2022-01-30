package com.robe.robebookodemia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class User : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        var btnBackUser = findViewById<Button>(R.id.btnBackUser)
        btnBackUser.setOnClickListener {
            finish()
        }
    }

}