package com.assign3.client
// Implements top-level functions to create, update, and retrieve TodoLists.

import org.json.JSONArray
import org.json.JSONObject
import java.net.URL


//private val TODOLIST_URL = URL("http://localhost:8080/todo/api/todolists")
//private val TASK_URL = URL("http://localhost:8080/todo/api/tasks")
private val URL = "http://localhost:8080/todo/api/"


fun getTodoLists(email: String, password: String): ArrayList<TodoList> {
    // GET from TODOLIST_URL
    val connection = TODOLIST_URL.openConnection()

    connection.setRequestethod("PUT")

    val writer = connection.outputStream.writer()

    writer.write("{\"email\":\"$email\",\"password\":\"$password\"")
    writer.close()

    val reader = connection.inputStream.reader()
    val response = reader.readText()
    reader.close()

    val json = JSONArray(response)

    val todoLists = ArrayList<TodoList>()
    for (i in 0 until json.length()) {
        todoLists.add(TodoList(json.getJSONObject(i)))
    }

    return todoLists
}


fun createTodoList(email: String, password: String, name: String): TodoList {
    // POST to TODOLIST_URL
    val connection = TODOLIST_URL.openConnection()
    connection.doOutput = true
    val writer = connection.outputStream.writer()

    writer.write("{\"email\":\"$email\",\"password\":\"$password\",\"name\":\"$name\"}")
    writer.close()

    val reader = connection.inputStream.reader()
    val response = reader.readText()
    reader.close()

    return TodoList(JSONObject(response))
}


fun addTask(email: String, password: String, todoListId: Long, task: String): TodoList {
    // POST to TASK_URL
    val connection = TASK_URL.openConnection()
    connection.doOutput = true
    val writer = connection.outputStream.writer()

    writer.write("{\"email\":\"$email\",\"password\":\"$password\",\"todoListId\":$todoListId,\"task\":\"$task\"}")
    writer.close()

    val reader = connection.inputStream.reader()
    val response = reader.readText()
    reader.close()

    return TodoList(JSONObject(response))
}