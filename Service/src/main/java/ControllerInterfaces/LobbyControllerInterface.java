package ControllerInterfaces;

import Persistence.User;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author User
 */
public interface LobbyControllerInterface {

    public void joinMatchmakingQueue();

    public void createCustomGame();

    public void joinCustomQueue(long ID);
    
    public void logout();
 
    
    
}
