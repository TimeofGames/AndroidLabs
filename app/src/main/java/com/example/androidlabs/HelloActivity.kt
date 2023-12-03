package com.example.androidlabs

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class HelloActivity : Activity(){
    private val TAG = this.javaClass.simpleName

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, Const.LifeCycle.ON_CREATE)
        setContentView(R.layout.activity_helloact)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val email = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val password = findViewById<EditText>(R.id.editTextTextPassword)
        val errorText = findViewById<TextView>(R.id.errorText)

        loginButton.setOnClickListener{
            Log.i(TAG, Const.HelloActivityMethods.ON_LOGIN_BUTTON_PRESSED)
            val rightEmail = "a@gmail.com"
            val rightPassword = "1"

            if(email.text.toString().equals(rightEmail) && password.text.toString().equals(rightPassword)){
                val secondActivity = Intent(this@HelloActivity, SecondActivity::class.java)
                secondActivity.putExtra(Const.Extra.SOME_INFO, "info from helloActivity")
                startActivity(secondActivity)
            }
            else{
                errorText.text = "Wrong email or password"
            }
        }
    }
    override fun onStart() {
        Log.i(TAG, Const.LifeCycle.ON_START)
        super.onStart()
    }

    override fun onResume() {
        Log.i(TAG, Const.LifeCycle.ON_RESUME)
        super.onResume()
    }

    override fun onPause() {
        Log.i(TAG, Const.LifeCycle.ON_PAUSE)
        super.onPause()
    }

    override fun onStop() {
        Log.i(TAG, Const.LifeCycle.ON_STOP)
        super.onStop()
    }

    override fun onDestroy() {
        Log.i(TAG, Const.LifeCycle.ON_DESTROY)
        super.onDestroy()
    }
}