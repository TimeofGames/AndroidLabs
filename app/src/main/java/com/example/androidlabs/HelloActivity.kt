package com.example.androidlabs

import android.app.Activity
import android.os.Bundle
import android.widget.Button

class HelloActivity : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helloact)
        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener {
            button1.text = "НАЖАТО!"
        }

    }

}