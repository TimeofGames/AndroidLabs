package com.example.androidlabs

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast


class SecondActivity : Activity() {
    private val TAG = this.javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, Const.LifeCycle.ON_CREATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        var selectedUsers = ArrayList<String>()
        val textListView = findViewById<ListView>(R.id.list)
        val myStringArray = ArrayList<String>()
        val textListAdapter: ArrayAdapter<String> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, myStringArray)
        textListView.adapter = textListAdapter
        val addButton = findViewById<Button>(R.id.addButton)
        val delButton = findViewById<Button>(R.id.deleteButton)
        val textToList = findViewById<EditText>(R.id.textToList)
        addButton.setOnClickListener {
            if (!textToList.text.isNullOrEmpty()) {
                textListAdapter.add(textToList.text.toString())
                textToList.text.clear()
            }
        }
        delButton.setOnClickListener {
            selectedUsers.forEach(textListAdapter::remove)
            selectedUsers.clear()
        }
        textListView.onItemClickListener =
            OnItemClickListener { parent, v, position, id ->
                val user = textListAdapter.getItem(position)
                if (textListView.isItemChecked(position)) user?.let { selectedUsers.add(it) }
                else selectedUsers.remove(user)
                textListView.setItemChecked(position, false)
            }
        Toast.makeText(this@SecondActivity,
            intent.getStringExtra(Const.Extra.SOME_INFO), Toast.LENGTH_SHORT).show()



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