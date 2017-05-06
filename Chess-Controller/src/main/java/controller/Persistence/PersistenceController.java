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
import org.hibernate.Query;



/**
 *
 * @author User
 */
public class PersistenceController {
    
    public PersistenceController(){
    
    
    }
    
    public void persistUser(User user){
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("hu.shadowwolf_Chess-Controller_jar_1.0PU");
        EntityManager manager=emf.createEntityManager();
        
        
        
        manager.persist(user);
        
        manager.close();
        emf.close();
    }

    
    
    public User getUserById(int id){
        User user=null;
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("hu.shadowwolf_Chess-Controller_jar_1.0PU");
        EntityManager manager=emf.createEntityManager();
        
        
        
        
        return user;
    
    }
    
    public User getUserByUserName(String username){
        User user=null;
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("hu.shadowwolf_Chess-Controller_jar_1.0PU");
        EntityManager manager=emf.createEntityManager();
        
        Query q=(Query) manager.createNativeQuery("select * from Users where username = ?");
        q.setParameter(username, 1);
        
        
        
        return user;
    
    }
}
