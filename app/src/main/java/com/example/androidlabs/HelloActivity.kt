package com.example.androidlabs

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HelloActivity : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helloact)
        var button1Clicks = 0
        var button2Clicks = 0
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val text = findViewById<TextView>(R.id.text)

        fun changeText(){
            text.text = "First button clicks = $button1Clicks \nSecond button clicks = $button2Clicks"
        }

        button1.setOnClickListener {
            button1Clicks+=1
            changeText()
        }
        button2.setOnClickListener {
            button2Clicks+=1
            changeText()
        }
    }



}