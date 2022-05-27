package com.assign3.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private lateinit var items: ArrayList<String>
    private lateinit var itemsAdapter: ArrayAdapter<String>
    private lateinit var lvItems: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // When activity starts prompt for an email and password

        // ADD HERE
        lvItems = findViewById<ListView>(R.id.lvItems)
        items = ArrayList<String>()
        itemsAdapter = ArrayAdapter(this,
				android.R.layout.simple_list_item_1, items)
        lvItems.adapter = itemsAdapter
        items.add("First Item")
        items.add("Second Item")
    }

    fun onAddItem(view: View) {
        val etNewItem = findViewById<View>(R.id.etNewItem) as EditText
        val itemText = etNewItem.text.toString()
        itemsAdapter.add(itemText)
    }
}