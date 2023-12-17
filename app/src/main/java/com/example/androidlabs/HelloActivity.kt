package com.example.androidlabs

import DatabaseHandler
import User
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
import kotlin.math.log

class HelloActivity : Activity(){
    private val TAG = this.javaClass.simpleName
    private val db = DatabaseHandler(this)
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        db.addUser(User(id = -1, login = "admin", pass = "pass"))
        db.getAllUsers()
        super.onCreate(savedInstanceState)
        Log.i(TAG, Const.LifeCycle.ON_CREATE)
        setContentView(R.layout.activity_helloact)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val email = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val password = findViewById<EditText>(R.id.editTextTextPassword)
        val errorText = findViewById<TextView>(R.id.errorText)

        loginButton.setOnClickListener{
            Log.i(TAG, Const.HelloActivityMethods.ON_LOGIN_BUTTON_PRESSED)


            if(db.findUser(User(id = -1, login = email.text.toString(), pass = password.text.toString()))){
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
        db.close()
        Log.i(TAG, Const.LifeCycle.ON_DESTROY)
        super.onDestroy()
    }
}