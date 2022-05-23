/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package list;

import auth.AuthService;
import http.Responses;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;
import jakarta.json.JsonStructure;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import task.TaskDAO;
import user.User;
import user.UserDAO;

/**
 *
 * @author logan
 */
@Named
@Path("/todolists")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoListResource {
    
    @EJB
    AuthService authService;
    
    @EJB
    TodoListDAO todoListDao;
    
    @EJB
    UserDAO userDao;
    
    @POST
    public String postTodoList(String body) {
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonStructure bodyJson = jsonReader.read();
        
        try {
            this.checkAuth(bodyJson);
        } catch (Exception e) {
            return Responses.getUnauthorisedResponse();
        }
        
        String email = ((JsonString) bodyJson.getValue("/email")).getString();
        String name = ((JsonString) bodyJson.getValue("/name")).getString();
        
        if (name == null || "".equals(name)) {
            return Responses.getMissingDataResponse();
        }
        
        TodoList list = todoListDao.createList(email, name);
        
        String jsonString;
        try(Writer writer = new StringWriter()) {
            Json.createWriter(writer).write(list.toJson());
            jsonString = writer.toString();
        } catch (IOException e) {
            jsonString = "{}";
        }
        
        return jsonString;
    }
    
    @POST
    @Path("/getuserlists")
    public String getUserTodoLists(String body) {
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonStructure bodyJson = jsonReader.read();
        
        try {
            this.checkAuth(bodyJson);
        } catch (Exception e) {
            return Responses.getUnauthorisedResponse();
        }
        
        String email = ((JsonString) bodyJson.getValue("/email")).getString();
        
        User user = userDao.getUser(email);
        List<TodoList> lists = user.getTodoLists();
        
        JsonArrayBuilder jsonLists = Json.createArrayBuilder();
        for (TodoList list : lists) {
            jsonLists.add(list.toJson());
        }
        
        String jsonString;
        try(Writer writer = new StringWriter()) {
            Json.createWriter(writer).write(jsonLists.build());
            jsonString = writer.toString();
        } catch (IOException e) {
            jsonString = "[]";
        }
        
        return jsonString;
    }
    
    @POST
    @Path("{listId}")
    public String getTodoList(@PathParam("listId") String listId, String body) {
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonStructure bodyJson = jsonReader.read();
        
        try {
            this.checkAuth(bodyJson);
        } catch (Exception e) {
            return Responses.getUnauthorisedResponse();
        }
        
        TodoList todoList = todoListDao.getList(Long.parseLong(listId));
        
        String jsonString;
        try(Writer writer = new StringWriter()) {
            Json.createWriter(writer).write(todoList.toJson());
            jsonString = writer.toString();
        } catch (IOException e) {
            jsonString = "{}";
        }
        
        System.out.println(jsonString);
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
