/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gameserver.NetworkMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
class GameController implements ControllerInterfaces.GameplayControllerInterface {

    private final ObjectOutputStream outStream;
    
    private boolean firstPlayer;

    GameController(ObjectOutputStream outStream, ObjectInputStream inStream) {
        this.outStream = outStream;

        try {
            outStream.writeObject(new NetworkMessage("GETPLAYERNUMBER"));
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void giveUp() {
        try {
            outStream.writeObject(new NetworkMessage("GIVEUP"));
            
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void askForValidation(int fromRow, int fromCol, int toRow, int toCol) {
        try {
            outStream.writeObject(new NetworkMessage("ISVALIDMOVE,"
                    + fromRow + ","
                    + fromCol + ","
                    + toRow + ","
                    + toCol));
            
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        
        
    }
    
    }

    public void movePiece(int fromRow, int fromCol, int toRow, int toCol){
        String answer = null;
        NetworkMessage networkAnswer;
        try {
            outStream.writeObject(new NetworkMessage("MOVEPIECE,"
                    + fromRow + ","
                    + fromCol + ","
                    + toRow + ","
                    + toCol));
            
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
