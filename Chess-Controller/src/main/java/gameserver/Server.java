/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import Persistence.User;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Server implements Runnable{
    
    private static int PORTNUMBER=5555;
    ArrayList<ServerThread> serverThreads;
    ServerSocket serverSocket;
    MatchMakingThread MMThread;
    
    public Server(){
        MMThread=new MatchMakingThread(this);
    }
    
    public Server(int port){
        PORTNUMBER=port;
        
    }

    public void run() {
        try {
            serverSocket=new ServerSocket(PORTNUMBER);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(serverSocket!=null){
            try {
                Logger.getLogger(Server.class.getName()).log(Level.INFO, "Awaiting connection...");
                ServerThread s=new ServerThread(serverSocket.accept(),this);
                serverThreads.add(s);
                s.run();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    }
    
    public void JoinMatchmaking(ServerThread playerThread){
        MMThread.Addplayer(playerThread);
        
    }
}
