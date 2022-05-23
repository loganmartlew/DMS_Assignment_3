package com.assign3.client
// Implements top-level functions to create, update, and retrieve TodoLists.

import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

private val SERVER_URL = "http://localhost:8080"

fun getTodoLists(): JSONArray {
    // TODO: create valid user request to server
    val response = URL(SERVER_URL).readText()
    return JSONArray(response)
}

fun createTodoList(name: String): JSONObject {
    // TODO: create valid user post to server
    val response = URL(SERVER_URL).readText()
    return JSONObject(response) // Returns the new TodoList
}

fun addTask(todoListId: Int, task: String): JSONObject {
    // TODO: create valid new task post to server
    val response = URL(SERVER_URL).readText()
    return JSONObject(response) // Returns the updated TodoList
}