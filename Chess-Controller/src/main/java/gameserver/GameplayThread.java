/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

/**
 *
 * @author User
 */
public class GameplayThread implements Runnable{

    public void run() {
        
        
    }
    
    String processMessage(NetworkMessage input){
        
        
        if(input.getMessage().startsWith("MOVEPIECE")){
            
        }else if(input.getMessage().startsWith("ISVALIDMOVE")){
        
        }else if(input.getMessage().startsWith("GIVEUP")){
        
        }else if(input.getMessage().startsWith("DUMMYMESSAGE")){
            
        }
        return null;
    
    }
    
}
