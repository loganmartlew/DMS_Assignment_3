package com.assign3.client

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import viewModelScope

class TodolistsActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolists)

        fetchTodolists()
    }

    fun fetchTodolists() {
        viewModelScope.launch(Dispatchers.IO) {
            val todolists = getTodoLists(email, password)
        }
    }
}