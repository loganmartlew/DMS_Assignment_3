/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package list;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author Logan
 */
@Stateless
public class TodoListDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public TodoList createList(String name) {
        TodoList newList = new TodoList();
        newList.setName(name);
        em.persist(newList);
        return newList;
    }
}
