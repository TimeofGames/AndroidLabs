package com.example.androidlabs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.Console

class HelloActivity : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helloact)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val email = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val password = findViewById<EditText>(R.id.editTextTextPassword)
        val errorText = findViewById<TextView>(R.id.errorText)


        loginButton.setOnClickListener{
            val rightEmail = "a@gmail.com"
            val rightPassword = "1"

            if(email.text.toString().equals(rightEmail) && password.text.toString().equals(rightPassword)){
                val secondActivity = Intent(this@HelloActivity, SecondActivity::class.java)
                startActivity(secondActivity)
            }
            else{
                errorText.text = "Wrong email or password"
            }
        }

    }



}