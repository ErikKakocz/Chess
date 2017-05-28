/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gameserver.NetworkMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class GameStateListener implements Runnable{
    
    ObjectInputStream inStream;
    
    public GameStateListener(ObjectInputStream inStream){
        this.inStream=inStream;
    }

    public void run() {
        String message;
        while (true)
            try {
                message=((NetworkMessage)inStream.readObject()).getMessage();
            } catch (IOException ex) {
                Logger.getLogger(GameStateListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GameStateListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
    
}
