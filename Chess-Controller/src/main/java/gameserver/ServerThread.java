/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import Persistence.User;
import controller.Persistence.PersistenceController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author User
 */
class ServerThread implements Runnable,Comparable{

    PersistenceController persistenceController;
    
    Socket socket=null;
    Server server;
    User user;
    
    public ServerThread() {
        
    }

    public ServerThread(Socket socket,Server server) {
        this.socket = socket;
        persistenceController=new PersistenceController();
        this.server=server;
        
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        ObjectInputStream inStream = null;
        ObjectOutputStream outStream = null;
        if(socket!=null)
        {
            try {
                inStream=new ObjectInputStream(socket.getInputStream());
                outStream=new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        while(!socket.isClosed())
        {
            
            try {
                outStream.writeObject(processInput((NetworkMessage)inStream.readObject()));
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private NetworkMessage processInput(NetworkMessage input) {
        NetworkMessage answer = null;
        if(input.getMessage().startsWith("LOGIN")){
            String uname=input.getMessage().split(",")[1];
            String pass=input.getMessage().split(",")[2];
            User user=persistenceController.getUserByUserName(uname);
            if(user!=null && BCrypt.checkpw(pass, user.getPassword())){
                answer=new NetworkMessage("LOGINSUCCES",user);
                this.user=user;
            }
        }
        else if(input.getMessage().startsWith("REGISTER")){
            
            if(persistenceController.getUserByUserName(((User)input.getAttachment()).getUsername()) != null)
                answer=new NetworkMessage("REGISTRATIONFAILURE,EXISTINGUSER");
            else if(persistenceController.getUserByEmail(((User)input.getAttachment()).getUsername()) != null)
                answer=new NetworkMessage("REGISTRATIONFAILURE,EXISTINGEMAIL");
            else{
                persistenceController.persistUser((User)input.getAttachment());
                answer=new NetworkMessage("REGISTRATIONSUCCES");
            }
        }
            
        return answer;
    }

    public int compareTo(Object o) {
        return(this.user.getELO()-((ServerThread)o).user.getELO());
    }

    public PersistenceController getPersistenceController() {
        return persistenceController;
    }

    public void setPersistenceController(PersistenceController persistenceController) {
        this.persistenceController = persistenceController;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    

    
    
}
