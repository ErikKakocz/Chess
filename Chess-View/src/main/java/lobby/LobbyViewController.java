/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby;

import ControllerInterfaces.LobbyControllerInterface;
import Persistence.User;

/**
 *
 * @author User
 */
public class LobbyViewController {

    LobbyControllerInterface LoContInt;
    User user;
    public LobbyViewController() {
        
    }
    
    public void setupInterface(Object ob){
        LoContInt=(LobbyControllerInterface)ob;
    
    }

    public void setUser(User user) {
        this.user=user;
    }
    
    public void playGame(){
        LoContInt.joinMatchmakingQueue();
    }
    
    public void createCustomGame(){
        LoContInt.createCustomGame();
    }
    
    public void playCustomGame(int ID){
        LoContInt.joinCustomQueue(ID);
    }

}
