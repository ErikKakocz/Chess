/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author User
 */
public class MatchMakingThread implements Runnable{

    Server server;
    ArrayList<ServerThread> usersWaitingToPlay;
    private boolean running;
    
    public MatchMakingThread(Server s) {
        server=s;
    }

    public void Addplayer(ServerThread player){
        usersWaitingToPlay.add(player);
    }
    
    public void run() {
        running=true;
        Collections.sort(usersWaitingToPlay);
        for(int i=0;i<usersWaitingToPlay.size();i++)
            for(int j=i+10;j<usersWaitingToPlay.size();j++){
                
            
            }
                
        
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    void gameStart(ServerThread ThreadOne, ServerThread ThreadTwo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
