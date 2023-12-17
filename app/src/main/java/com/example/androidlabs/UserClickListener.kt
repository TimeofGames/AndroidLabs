package com.example.androidlabs

import User

interface UserClickListener {
    fun onItemClick(user: User)
    fun onMenuDeleteClick(user: User)
    fun onMenuUpdateClick(user: User)
}
