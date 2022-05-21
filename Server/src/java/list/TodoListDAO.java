/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package list;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import user.User;
import user.UserDAO;

/**
 *
 * @author Logan
 */
@Stateless
public class TodoListDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private UserDAO userDao;
    
    public TodoList createList(String userEmail, String name) {
        User user = userDao.getUser(userEmail);
        
        TodoList newList = new TodoList();
        newList.setName(name);
        newList.setUser(user);
        em.persist(newList);
        
        return newList;
    }
    
    public TodoList getList(Long id) {
        TodoList todoList = em.find(TodoList.class, id);
        return todoList;
    }
}
