package com.example.androidlabs

import DatabaseHandler
import ListElementAdapter
import User
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.concurrent.thread


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

    private val looper = Looper.getMainLooper()
    private val message = Message.obtain()
    private val handler = object: Handler(looper) {
        override fun handleMessage(msg: Message) {
            if(msg.sendingUid == 1){
                val users = ArrayList<User>()
                (msg.obj as? ArrayList<*>)?.forEach {
                    users.add(it as User)
                }
                rvUsers.post{userAdapter.setData(users)}
                }
        }
    }
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
                DBRequestHandler(handler, db).updateUser(user,oldUser)
                isUpdate = false
                this.login.isEnabled = true
            }
            else {
                val login = login.text.toString()
                val pass = password.text.toString()
                val user = User(login = login, pass = pass)
                DBRequestHandler(handler, db).addUser(user)
            }
            login.post{login.text.clear()}
            password.post{password.text.clear()}
        }

        btnLoad.setOnClickListener {
            DBRequestHandler(handler, db).getAllUsers()
        }
        userAdapter.setListener(object: UserClickListener {
            override fun onItemClick(user: User) {
                Toast.makeText(this@SecondActivity, "onItemClick() user=$user", Toast.LENGTH_SHORT).show()
            }

            override fun onMenuDeleteClick(user: User) {
                thread {db.deleteUser(user)}
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