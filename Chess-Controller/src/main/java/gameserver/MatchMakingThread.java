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
public class MatchMakingThread implements Runnable {

    Server server;
    ArrayList<ServerThread> usersWaitingToPlay;
    private boolean running;

    public MatchMakingThread(Server s) {
        usersWaitingToPlay=new ArrayList<ServerThread>();
        server = s;
    }

    public void Addplayer(ServerThread player) {
        usersWaitingToPlay.add(player);
    }

    public void run() {
        running = true;
        while (running) {
            Collections.sort(usersWaitingToPlay);
            if (usersWaitingToPlay.size() > 1) 
                server.startGame(usersWaitingToPlay.remove(1), usersWaitingToPlay.remove(0));
                
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
