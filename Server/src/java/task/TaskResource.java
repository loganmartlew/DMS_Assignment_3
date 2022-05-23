/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task;

import auth.AuthService;
import http.Responses;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.json.Json;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;
import jakarta.json.JsonStructure;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import list.TodoList;
import list.TodoListDAO;

/**
 *
 * @author Logan
 */
@Named
@Path("/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    @EJB
    AuthService authService;
    
    @EJB
    TaskDAO taskDao;
    
    @EJB
    TodoListDAO todoListDao;

    @POST
    public String postTask(String body) {
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonStructure bodyJson = jsonReader.read();
        
        try {
            this.checkAuth(bodyJson);
        } catch (Exception e) {
            return Responses.getUnauthorisedResponse();
        }
        
        String listId = ((JsonString) bodyJson.getValue("/listId")).getString();
        String name = ((JsonString) bodyJson.getValue("/name")).getString();
        
        if (listId == null || "".equals(listId) || name == null || "".equals(name)) {
            return Responses.getMissingDataResponse();
        }
        
        taskDao.createTask(Long.parseLong(listId), name);
        TodoList list = todoListDao.getList(Long.parseLong(listId));
        
        String jsonString;
        try(Writer writer = new StringWriter()) {
            Json.createWriter(writer).write(list.toJson());
            jsonString = writer.toString();
        } catch (IOException e) {
            jsonString = "{}";
        }
        
        return jsonString;
    }
    
    @PATCH
    @Path("{taskId}")
    public String toggleTask(@PathParam("taskId") String taskId, String body) {
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonStructure bodyJson = jsonReader.read();
        
        try {
            this.checkAuth(bodyJson);
        } catch (Exception e) {
            return Responses.getUnauthorisedResponse();
        }
        
        Task task = taskDao.toggleComplete(Long.parseLong(taskId));
        
        String jsonString;
        try(Writer writer = new StringWriter()) {
            Json.createWriter(writer).write(task.toJson());
            jsonString = writer.toString();
        } catch (IOException e) {
            jsonString = "{}";
        }
        
        return jsonString;
    }

    private void checkAuth(JsonStructure bodyJson) throws Exception {
        if (!authService.isLoggedIn()) {
            String email = ((JsonString) bodyJson.getValue("/email")).getString();
            String password = ((JsonString) bodyJson.getValue("/password")).getString();

            if (email == null || "".equals(email)) {
                throw new NullPointerException();
            }

            if (password == null || "".equals(password)) {
                throw new NullPointerException();
            }

            authService.login(email, password);
        }
    }
}
