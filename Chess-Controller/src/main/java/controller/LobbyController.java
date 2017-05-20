/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ControllerInterfaces.LobbyControllerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import MenuAssets.LobbyGame;


/**
 *
 * @author User
 */
class LobbyController implements LobbyControllerInterface{

    Controller control;
    ObservableList<LobbyGame> gamesList;
    
    public Controller getControl() {
        return control;
        
    }

    public void setControl(Controller control) {
        this.control = control;
    }
    
    public LobbyController(Controller aThis) {
        control=aThis;
        gamesList=FXCollections.observableArrayList();
    }

    public void joinMatchmakingQueue() {
        control.sendMatchmakingJoinRequest();
    }

    public void createCustomGame(){
        control.sendCreateCustomGameRequest();
    }

    public void joinCustomQueue(int ID) {
        control.sendJoinGameRequest(ID);
    }

    public ObservableList<LobbyGame> getGamesList() {
        return gamesList;
    }

    public void setGamesList(ObservableList<LobbyGame> gamesList) {
        this.gamesList = gamesList;
    }

    
    
}
