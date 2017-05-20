/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ControllerInterfaces.RegistrationControllerInterface;
import Persistence.User;
import java.util.List;

/**
 *
 * @author User
 */
public class RegistrationController implements RegistrationControllerInterface{

    Controller controller;
    
    public RegistrationController(){
        
    
    }

    public RegistrationController(Controller cont){
        controller=cont;
    
    }
    
    public User getUser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<User> getUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void persistUser(String username, String nick, String pass, String email) {
        
    }

    
}
