/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auth;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;
import user.User;
import user.UserDAO;

/**
 *
 * @author Logan
 */
@Stateful
public class AuthService {
    
    private String userEmail;
    
    @EJB
    UserDAO userDao;
    
    public void login(String email, String password) {
        User user = userDao.getUser(email);
        if (password.equals(user.getPassword())) {
            this.userEmail = user.getEmail();
        }
    }
    
    public void signup(String email, String password) {
        User newUser = userDao.createUser(email, password);
        this.userEmail = newUser.getEmail();
    }
    
    public void logout() {
        this.userEmail = null;
    }
    
    public boolean isLoggedIn() {
        return userEmail != null;
    }
}
