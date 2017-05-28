/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Persistence;

import Persistence.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
/**
 *
 * @author User
 */
public class PersistenceController {
    
    EntityManagerFactory emf;
        EntityManager manager;
        
    
    
    public PersistenceController(){
        emf=Persistence.createEntityManagerFactory("hu.shadowwolf_Chess-Controller_jar_1.0PU");
        manager=emf.createEntityManager();
    }
    
    public void persistUser(User user){
        manager.getTransaction().begin();
        manager.persist(user);
        manager.getTransaction().commit();
    }

    
    
    public User getUserById(int id){
        User user=null;
        
        TypedQuery<User> q=(TypedQuery<User>) manager.createQuery("select u from UserEntity u where u.id = ?1 ");
        q.setParameter(id, 1);
        user=q.getSingleResult();
        
        return user;
    
    }
    
    public User getUserByUserName(String username){
        User user=null;
        
        
        TypedQuery<User> q=(TypedQuery<User>) manager.createQuery("select u from UserEntity u where u.username like ?1 ");
        q.setParameter(1,username);
        if(q.getResultList().size()>0)
            user=q.getSingleResult();
        
        return user;
    
    }

    public User getUserByEmail(String mail){
        User user=null;
        
        TypedQuery<User> q=(TypedQuery<User>) manager.createQuery("select u from UserEntity u where email like ?1 ");
        q.setParameter(1,mail);
        if(q.getResultList().size()>0)
            user=q.getSingleResult();
        
        return user;
    }
    
    
    public boolean contains(User user) {
        return getUserByUserName(user.getName())!=null || getUserByEmail(user.getEmail())!=null;
    }
    
    public void close(){
        manager.close();
        emf.close();
    }
    
    
}
