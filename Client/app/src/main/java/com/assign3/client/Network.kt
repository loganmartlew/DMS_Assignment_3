package com.assign3.client
// Implements top-level functions to create, update, and retrieve TodoLists.

import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL


private const val ROOT_URL = "http://localhost:8080/todo/api/"

private const val SIGNUP_URL = ROOT_URL + "auth/signup"  // POST
private const val TASK_URL = ROOT_URL + "tasks"  // POST, PUT
private const val TODOLIST_URL = ROOT_URL + "todolists"  // POST
private const val GET_TODOLIST_URL = ROOT_URL + "todolists/getuserlists"  // POST

fun signup(email: String, password: String) {
    // POST /auth/signup - creates a new user, returns nothing. Needs email, password

    val connection = URL(SIGNUP_URL).openConnection() as HttpURLConnection
    connection.doOutput = true
    connection.requestMethod = "POST"

    val writer = connection.outputStream.writer()

    writer.write("{\"email\":\"$email\",\"password\":\"$password\"")
    writer.close()
}

fun addTask(email: String, password: String, listId: Long, task: String): TodoList {
    // POST /tasks - creates a new task, returns the list the task was added to. Needs email, password, listId, name

    val connection = URL(TASK_URL).openConnection() as HttpURLConnection
    connection.requestMethod = "POST"
    connection.doOutput = true

    val writer = connection.outputStream.writer()

    writer.write("{\"email\":\"$email\",\"password\":\"$password\",\"listId\":$listId,\"task\":\"$task\"}")
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
    // POST /todolists - creates a new todolist, returns the new list. Needs email, password, name

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


fun getTodoLists(email: String, password: String): ArrayList<TodoList> {
    // POST /todolists/getuserlists - returns all todolists for the user. Needs email, password

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


fun getTodoList(email: String, password: String, listId: Long): TodoList {
    // POST /todolists/{listId} - returns the todolist with the given id. Needs email, password, listId as path parameter

    val url = "$TODOLIST_URL/$listId"
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