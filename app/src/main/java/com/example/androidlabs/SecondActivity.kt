package com.example.androidlabs

import DatabaseHandler
import ListElementAdapter
import User
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class SecondActivity : Activity() {
    private val TAG = this.javaClass.simpleName

    private var isUpdate = false
    private lateinit var oldUser:User

    private lateinit var login: EditText
    private lateinit var password: EditText
    private lateinit var btnSave: Button
    private lateinit var btnLoad: Button
    private lateinit var rvUsers: RecyclerView
    private val userAdapter = ListElementAdapter()


    private val db = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        login = findViewById(R.id.login)
        password = findViewById(R.id.password)
        btnSave = findViewById(R.id.addButton)
        btnLoad = findViewById(R.id.showAllButton)

        btnSave.setOnClickListener {
            if(isUpdate){
                val login = login.text.toString()
                val pass = password.text.toString()
                val user = User(login = login, pass = pass)
                db.updateUser(user, oldUser)
                isUpdate = false
                this.login.isEnabled = true
            }
            else {
            val login = login.text.toString()
            val pass = password.text.toString()
            val user = User(login = login, pass = pass)
            db.addUser(user)
            }
            login.text.clear()
            password.text.clear()
        }

        btnLoad.setOnClickListener {
            val users = db.getAllUsers()
            val usersLog = users.joinToString(separator = ",\n")
            Log.d(TAG, "Users:\n $usersLog")
            userAdapter.setData(users)
        }
        userAdapter.setListener(object: UserClickListener {
            override fun onItemClick(user: User) {
                Toast.makeText(this@SecondActivity, "onItemClick() user=$user", Toast.LENGTH_SHORT).show()
            }

            override fun onMenuDeleteClick(user: User) {
                db.deleteUser(user)
                Toast.makeText(this@SecondActivity, "onMenuDeleteClick() user=$user", Toast.LENGTH_SHORT).show()
            }

            override fun onMenuUpdateClick(user: User) {
                isUpdate = true
                oldUser = user
                login.setText(user.login)
                login.isEnabled = false
                password.setText(user.pass)
                Toast.makeText(this@SecondActivity, "onMenuDeleteClick() user=$user", Toast.LENGTH_SHORT).show()
            }
        })


        rvUsers = findViewById(R.id.rv_users)
        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvUsers.adapter = userAdapter

    }

    override fun onPause() {
        super.onPause()
        db.deleteAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        db.deleteAll()
        db.close()
    }
}