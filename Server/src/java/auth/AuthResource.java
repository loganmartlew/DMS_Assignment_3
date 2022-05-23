/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auth;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;
import jakarta.json.JsonStructure;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.io.StringReader;

/**
 *
 * @author Logan
 */
@Named
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    @Context
    private HttpServletRequest request;
    
    @EJB
    AuthService authService;
    
    @POST
    @Path("/login")
    public String login(String body) {
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonStructure bodyJson = jsonReader.read();
        
        String email = ((JsonString) bodyJson.getValue("/email")).getString();
        String password = ((JsonString) bodyJson.getValue("/password")).getString();
        
        return "";
    }
    
    @POST
    @Path("/signup")
    public String signup(String body) {
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonStructure bodyJson = jsonReader.read();
        
        String email = ((JsonString) bodyJson.getValue("/email")).getString();
        String password = ((JsonString) bodyJson.getValue("/password")).getString();
        
        authService.signup(email, password);
        
        return "Success";
    }
}
