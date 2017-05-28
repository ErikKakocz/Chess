/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import controller.Persistence.PersistenceController;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
class ConnectionListenerThread implements Runnable{

    Server server;
    ServerSocket serverSocket;
    private int PORTNUMBER=5555;
    PersistenceController persistenceController;
    
    
    public ConnectionListenerThread(Server serv,PersistenceController persistenceController){
        server=serv;
        this.persistenceController=persistenceController;
    }
    
    public ConnectionListenerThread(Server serv,PersistenceController persistenceController,int port){
        server=serv;
        this.persistenceController=persistenceController;
        PORTNUMBER=port;
    }
    
    public void run() {
        
        try {
            
            serverSocket=new ServerSocket(PORTNUMBER);
            while(true){
                try {
                    Logger.getLogger(Server.class.getName()).log(Level.INFO, "Awaiting connection...");
                    ServerThread st=new ServerThread(serverSocket.accept(),server);
                    st.setPersistenceController(persistenceController);
                    Thread s=new Thread(st);
                    server.addServerThread(s);
                    
                    Logger.getLogger(Server.class.getName()).log(Level.INFO, "ConnectionRequest: thread created");
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    break;
                }
                
            }
        } catch (IOException ex) {        
            Logger.getLogger(ConnectionListenerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
