/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author Logan
 */
@Stateless
public class TaskDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public Task createTask(String name) {
        Task newTask = new Task();
        newTask.setName(name);
        newTask.setIsComplete(false);
        em.persist(newTask);
        return newTask;
    }
    
    public Task setComplete(Long id, boolean complete) {
        Task task = em.find(Task.class, id);
        task.setIsComplete(complete);
        em.persist(task);
        return task;
    }
    
    public Task toggleComplete(Long id) {
        Task task = em.find(Task.class, id);
        task.setIsComplete(!task.isIsComplete());
        em.persist(task);
        return task;
    }
}
