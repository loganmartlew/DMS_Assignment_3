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
import jakarta.json.JsonStructure;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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
        
        String listId = bodyJson.getValue("/listId").toString();
        String name = bodyJson.getValue("/name").toString();
        
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

    private void checkAuth(JsonStructure bodyJson) throws Exception {
        if (!authService.isLoggedIn()) {
            String email = bodyJson.getValue("/email").toString();
            String password = bodyJson.getValue("/password").toString();

            if (email == null || email == "") {
                throw new NullPointerException();
            }

            if (password == null || password == "") {
                throw new NullPointerException();
            }

            authService.login(email, password);
        }
    }
}
