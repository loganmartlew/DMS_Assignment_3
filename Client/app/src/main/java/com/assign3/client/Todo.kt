package com.assign3.client

import org.json.JSONObject

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