package com.assign3.client
// Implements top-level functions to create, update, and retrieve TodoLists.

import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL


//private val TODOLIST_URL = URL("http://localhost:8080/todo/api/todolists")

private val SIGNUP_URL = "http://localhost:8080/todo/api/auth/signup"  // POST
private val TASK_URL = "http://localhost:8080/todo/api/tasks"  // POST, PUT
private val TODOLIST_URL = "http://localhost:8080/todo/api/todolists"  // POST
private val GET_TODOLIST_URL = "http://localhost:8080/todo/api/todolists/getuserlists"  // POST


fun signup(email: String, password: String) {
    // POST /auth/signup - creates a new user, returns nothing. Needs email, password

    val connection = URL(SIGNUP_URL).openConnection() as HttpURLConnection
    connection.doOutput = true
    connection.requestMethod = "POST"

    val writer = connection.outputStream.writer()

    writer.write("{\"email\":\"$email\",\"password\":\"$password\"")
    writer.close()
}

fun addTask(email: String, password: String, todoListId: Long, task: String): TodoList {
    // POST /tasks - creates a new task, returns the list the task was added to. Needs email, password, listId, name

    val connection = URL(TASK_URL).openConnection() as HttpURLConnection
    connection.requestMethod = "POST"
    connection.doOutput = true

    val writer = connection.outputStream.writer()

    writer.write("{\"email\":\"$email\",\"password\":\"$password\",\"todoListId\":$todoListId,\"task\":\"$task\"}")
    writer.close()

    val reader = connection.inputStream.reader()
    val response = reader.readText()
    reader.close()

    return TodoList(JSONObject(response))
}


fun toggleTaskCompletion(email: String, password: String, taskId: Long): Task {
    // PUT /tasks/{taskId} - toggles the completed state of a task, returns the updated task. Needs email, password, taskId as path parameter

    val url = "$TASK_URL/$taskId"
    val connection = URL(url).openConnection() as HttpURLConnection
    connection.requestMethod = "PUT"
    connection.doOutput = true

    val writer = connection.outputStream.writer()

    writer.write("{\"email\":\"$email\",\"password\":\"$password\"")
    writer.close()

    val reader = connection.inputStream.reader()
    val response = reader.readText()
    reader.close()

    return Task(JSONObject(response))
}


fun createTodoList(email: String, password: String, name: String): TodoList {
    // POST /todolists - creates a new todo list, returns the new list. Needs email, password, name

    val connection = URL(TODOLIST_URL).openConnection() as HttpURLConnection
    connection.requestMethod = "POST"
    connection.doOutput = true

    val writer = connection.outputStream.writer()

    writer.write("{\"email\":\"$email\",\"password\":\"$password\",\"name\":\"$name\"}")
    writer.close()

    val reader = connection.inputStream.reader()
    val response = reader.readText()
    reader.close()

    return TodoList(JSONObject(response))
}


fun getTodoLists(email: String, password: String): List<TodoList> {
    // POST /todolists/getuserlists - returns all todo lists for the user. Needs email, password

    val connection = URL(GET_TODOLIST_URL).openConnection() as HttpURLConnection
    connection.requestMethod = "POST"
    connection.doOutput = true

    val writer = connection.outputStream.writer()

    writer.write("{\"email\":\"$email\",\"password\":\"$password\"}")
    writer.close()

    val reader = connection.inputStream.reader()
    val response = reader.readText()
    reader.close()

    val jsonArray = JSONArray(response)
    val todoLists = ArrayList<TodoList>()

    for (i in 0 until jsonArray.length()) {
        todoLists.add(TodoList(jsonArray.getJSONObject(i)))
    }

    return todoLists
}


fun getTodoList(email: String, password: String, todoListId: Long): TodoList {
    // POST /todolists/{todoListId} - returns the todo list with the given id. Needs email, password, todoListId as path parameter

    val url = "$TODOLIST_URL/$todoListId"
    val connection = URL(url).openConnection() as HttpURLConnection
    connection.requestMethod = "POST"
    connection.doOutput = true

    val writer = connection.outputStream.writer()

    writer.write("{\"email\":\"$email\",\"password\":\"$password\"}")
    writer.close()

    val reader = connection.inputStream.reader()
    val response = reader.readText()
    reader.close()

    return TodoList(JSONObject(response))
}