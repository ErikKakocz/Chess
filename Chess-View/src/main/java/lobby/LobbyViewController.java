/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby;

import ControllerInterfaces.LobbyControllerInterface;
import MenuAssets.LobbyGame;
import Persistence.User;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class LobbyViewController {

    LobbyControllerInterface LoContInt;
    User user;
    
    @FXML
    Button PlayButton;
    
    @FXML
    Button CreateCustomGameButton;
    
    @FXML
    Button JoinCustomGameButton;
    
    @FXML
    Label LoggedInAsField;
    
    @FXML
    Button QuitButton;
    
    @FXML
    ListView GamesList;
    
    public LobbyViewController() {
        
    }
    
    public void setupInterface(Object ob){
        LoContInt=(LobbyControllerInterface)ob;
        
        PlayButton.setOnAction(new EventHandler(){
            public void handle(Event event) {
                playGame();
            }
        });
        CreateCustomGameButton.setOnAction(new EventHandler(){
            public void handle(Event event) {
                createCustomGame();
            }
        });
        JoinCustomGameButton.setOnAction(new EventHandler(){
            public void handle(Event event) {
                LobbyGame game=(LobbyGame)(GamesList.getItems().get(GamesList.getEditingIndex()));
                joinCustomGame(game.getGameID());
            }
        });
        QuitButton.setOnAction(new EventHandler(){
            public void handle(Event event) {
                LoContInt.logout();
                Stage stage = (Stage) QuitButton.getScene().getWindow();
            }
        });
        
    }

    public void setUser(User user) {
        this.user=user;
        LoggedInAsField.setText(user.getName());
    }
    
    public void playGame(){
        LoContInt.joinMatchmakingQueue();
    }
    
    public void createCustomGame(){
        LoContInt.createCustomGame();
    }
    
    public void joinCustomGame(long ID){
        LoContInt.joinCustomQueue(ID);
    }

    
}
