/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;

/**
 *
 * @author Logan
 */
@Stateless
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    public User createUser(String email, String password) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        em.persist(newUser);
        return newUser;
    }

    public User getUser(String email) {
        User user = em.find(User.class, email);
        return user;
    }
    
    public List<User> getAllUsers() {
        Query query = em.createQuery("SELECT u FROM User u", User.class);
        List<User> users = query.getResultList();
        return users;
    }
}
