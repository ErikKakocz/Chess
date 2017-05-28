/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ControllerInterfaces.RegistrationControllerInterface;
import Exceptions.InvalidRegistrationException;
import Persistence.User;
import gameserver.NetworkMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class RegistrationController implements RegistrationControllerInterface{

    
    
    public RegistrationController(){
    
    }

    public void persistUser(String username, String nick, String pass, String email) throws InvalidRegistrationException{
        
        User user=new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setName(nick);
        user.setPassword(pass);
        try {
            Socket socket = new Socket(Inet4Address.getLocalHost(), 5555); //TODO:Lecserélni 

            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            outStream.flush();
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            

            Thread.sleep(500);
            Logger.getLogger(Controller.class.getName()).log(Level.INFO,"ZZZzzzzzzzz...");
            outStream.writeObject(new NetworkMessage("REGISTER", user));
            NetworkMessage result=(NetworkMessage)inStream.readObject();
            if(result.getMessage().equals("REGISTRATIONFAILURE,EXISTINGUSER")){    
                InvalidRegistrationException ex=new InvalidRegistrationException();
                ex.setMessage("Existing User");
                throw ex;
            }
            if(result.getMessage().equals("REGISTRATIONFAILURE,EXISTINGEMAIL")){    
                InvalidRegistrationException ex=new InvalidRegistrationException();
                ex.setMessage("Existing Email");
                throw ex;
            }
            
            socket.close();
                
                
        } catch(IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
}
