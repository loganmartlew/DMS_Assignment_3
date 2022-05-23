/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

/**
 *
 * @author Logan
 */
@Named
@Path("/users")
public class UserResource {
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private UserDAO userDao;
    
    public UserResource() {};
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllUsers() throws IOException {
        JsonArrayBuilder array = Json.createArrayBuilder();
        List<User> users = userDao.getAllUsers();
        
        for(User user : users) {
            array.add(user.toJson());
        }
        
        String jsonString;
        try(Writer writer = new StringWriter()) {
            Json.createWriter(writer).write(array.build());
            jsonString = writer.toString();
        }
        
        return jsonString;
    }
}
