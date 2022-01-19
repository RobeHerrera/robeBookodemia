package com.robe.robebookodemia

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class DetailBooks : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_books)
    }

    fun goBack(view: View){
        //these two methods made the back action
        finish()
        //super.onBackPressed();
    }
}