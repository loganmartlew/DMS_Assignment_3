package com.assign3.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.json.JSONObject
import org.json.JSONTokener

class Task(json: JSONObject) {
    init {
        val id: Long = json.getLong("id")
        val name: String = json.getString("name")
        val isComplete: Boolean = json.getBoolean("isComplete")
    }
}

class TodoList(json: JSONObject) {
    init {
        val id = json.getLong("id")
        val name = json.getString("name")
        val tasks = ArrayList<Task>()
        val jsonTasks = json.getJSONArray("tasks")
        for (i in 0 until jsonTasks.length()) {
            val jsonTask = jsonTasks.getJSONObject(i)
            tasks.add(Task(jsonTask))
        }
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonArray = JSONTokener(response).nextValue() as JSONArray

        // Create a list of TodoList objects from the JSONArray
        val todoList = ArrayList<TodoList>()
        for (i in 0 until jsonArray.length()) {
            todoList.add(TodoList(jsonArray.getJSONObject(i)))
        }

//        // ADD HERE
//        lvItems = (ListView) findViewById(R.id.lvItems)
//        items = new ArrayList<String>()
//        itemsAdapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_list_item_1, items)
//        lvItems.setAdapter(itemsAdapter)
//        items.add("First Item")
//        items.add("Second Item")
    }
}