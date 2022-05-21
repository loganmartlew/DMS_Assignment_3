/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author Logan
 */
@Named
@Path("/users")
public class UserResource {
    
    @EJB
    private UserDAO userDao;
    
    public UserResource() {};
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllUsers() {
        return "[]";
    }
}
