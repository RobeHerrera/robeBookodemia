package com.robe.robebookodemia

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class NewAccount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account)
        // Get the Intent that started this activity and extract the string
        //val message = intent.getStringExtra(EXTRA_MESSAGE)

        // Capture the layout's TextView and set the string as its text
//        val textView = findViewById<TextView>(R.id.textView).apply {
//            text = message
//        }
        println(" MENSAJE DEL MAS ALLÁ ......")
        //Toast.makeText(this, message,Toast.LENGTH_SHORT)
    }

    fun goBack(view: View){
        //these two methods made the back action
        finish()
        //super.onBackPressed();
    }

}