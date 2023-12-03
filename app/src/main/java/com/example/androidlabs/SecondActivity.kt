package com.example.androidlabs

import ListElement
import ListElementAdapter
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.size


class SecondActivity : Activity() {
    private val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, Const.LifeCycle.ON_CREATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        val textListView = findViewById<ListView>(R.id.list)
        val addButton = findViewById<Button>(R.id.addButton)
        val delButton = findViewById<Button>(R.id.deleteButton)
        val textToList = findViewById<EditText>(R.id.textToList)
        val myStringArray = ArrayList<ListElement>()
        val textListAdapter: ListElementAdapter<String> =
            ListElementAdapter(myStringArray, this)
        val sharedPref: SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        var number = 0
        var restoredData = sharedPref.getString(Const.Extra.LIST_DATA + number, "")
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(Const.Extra.LIST_DATA + number)
        while (restoredData != "") {
            textListAdapter.add(ListElement(restoredData,false))
            number++
            restoredData = sharedPref.getString(Const.Extra.LIST_DATA + number, "")
            editor.remove(Const.Extra.LIST_DATA + number).apply()
        }
        textListView.adapter = textListAdapter

        addButton.setOnClickListener {
            if (!textToList.text.isNullOrEmpty()) {
                textListAdapter.add(ListElement(textToList.text.toString(),false))
                textToList.text.clear()
            }
        }
        delButton.setOnClickListener {
            var itemsToDel = ArrayList<ListElement>()
            for(i in 0..textListAdapter.count-1){
               if(textListAdapter.getItem(i).checked){
                   itemsToDel.add(textListAdapter.getItem(i))
               }
            }
            itemsToDel.forEach(textListAdapter::remove)
        }
        Toast.makeText(
            this@SecondActivity,
            intent.getStringExtra(Const.Extra.SOME_INFO), Toast.LENGTH_SHORT
        ).show()


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
        val textListView = findViewById<ListView>(R.id.list)
        val sharedPref: SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        if (textListView.size != 0){
            for(i in 0..textListView.size-1){
                editor.putString(Const.Extra.LIST_DATA + i,
                    (textListView.adapter.getItem(i)as ListElement).name
                ).apply()
            }
        }
        super.onStop()
    }

    override fun onDestroy() {
        Log.i(TAG, Const.LifeCycle.ON_DESTROY)
        super.onDestroy()
    }
}