package com.example.androidlabs

import DatabaseHandler
import User
import android.os.Handler
import android.os.Message
import kotlin.concurrent.thread

class DBRequestHandler(val handler: Handler, val db: DatabaseHandler) {
    val message:Message = Message.obtain()

    fun updateUser(user:User ,oldUser:User){
        thread {
            db.updateUser(user, oldUser)
        }
    }

    fun addUser(user: User){
        thread {
            db.addUser(user)
        }
    }

    fun getAllUsers(){
        thread {
            val users = db.getAllUsers()
            message.sendingUid = 1
            message.obj = users
            handler.sendMessage(message)
        }
    }
}