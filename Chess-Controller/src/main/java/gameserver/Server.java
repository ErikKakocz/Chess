/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import controller.Persistence.PersistenceController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Server implements Runnable{
    
    private static int PORTNUMBER=5555;
    ArrayList<Thread> serverThreads;
    Thread MMRunner;
    MatchMakingThread MMThread;
    PersistenceController persistenceController;
    ConnectionListenerThread ConnectionThread;
    
    public Server(){
        MMThread=new MatchMakingThread(this);
        MMRunner=new Thread(MMThread);
        serverThreads=new ArrayList<Thread>();
        persistenceController=new PersistenceController();
        ConnectionThread=new ConnectionListenerThread(this,persistenceController);
    }
    
    public Server(int port){
        PORTNUMBER=port;
        MMThread=new MatchMakingThread(this);
        MMRunner=new Thread(MMThread);
        serverThreads=new ArrayList<Thread>();
        persistenceController=new PersistenceController();
        ConnectionThread=new ConnectionListenerThread(this,persistenceController);
    }

    public void run() {
            Thread connd=new Thread(ConnectionThread);
            connd.start();
            MMRunner.start();
            BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
            while(true){
                try {
                    processInput(reader.readLine());
                    
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
    }
    
    
    void shutdown(){
        persistenceController.close();
        Logger.getLogger(Server.class.getName()).log(Level.INFO, "Server shutting down");
        System.exit(0);
    }
    
    public void JoinMatchmaking(ServerThread playerThread){
        MMThread.Addplayer(playerThread);
    }

    void addServerThread(Thread s) {
        serverThreads.add(s);
        s.start();
    }

    private void processInput(String readLine) {
        if(readLine.equals("shutdown"))
            shutdown();
    }
    
    void startGame(ServerThread player1, ServerThread player2) {
        GameplayController gameplayController = new GameplayController(player1, player2);
        player1.setGameplayController(gameplayController);
        player2.setGameplayController(gameplayController);
        player1.setWaitingOnMatch(false);
        player2.setWaitingOnMatch(false);
    }
}
