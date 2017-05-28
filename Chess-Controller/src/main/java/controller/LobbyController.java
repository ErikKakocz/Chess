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
import gameserver.NetworkMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
class LobbyController implements LobbyControllerInterface {

    Controller control;
    ObservableList<LobbyGame> gamesList;
    ObjectOutputStream outStream;
    ObjectInputStream inStream;
    Socket socket;

    public LobbyController() {
        gamesList = FXCollections.observableArrayList();

    }

    LobbyController(Socket sock,ObjectOutputStream outStream, ObjectInputStream inStream) {
        this();
        this.inStream = inStream;
        this.outStream = outStream;
        socket=sock;
    }

    public Controller getControl() {
        return control;

    }

    public void setControl(Controller control) {
        this.control = control;
    }

    public void joinMatchmakingQueue() {
        try {
            outStream.writeObject(new NetworkMessage("JOINMATCHMAKING"));
            if(((NetworkMessage)inStream.readObject()).getMessage().equals("MATCHFOUND")){
                Stage lobbyStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                

                loader.setLocation(RegistrationController.class.getResource("/fxml/game.fxml"));

                lobbyStage.setTitle("ChessMaster");
                GameController gameCont = new GameController(outStream,inStream);

                AnchorPane ancpane = null;
                try {
                    ancpane = (AnchorPane) loader.load();

                } catch (Exception e) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
                }

                Scene scene = new Scene(ancpane);
                lobbyStage.setScene(scene);
                lobby.LobbyViewController controller = loader.<lobby.LobbyViewController>getController();
                controller.setupInterface(gameCont);

                lobbyStage.show();
            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    

    public void createCustomGame() {
        try {
            outStream.writeObject(new NetworkMessage("CREATECUSTOMGAME"));
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void joinCustomQueue(long ID) {
        try {
            outStream.writeObject(new NetworkMessage("JOINCUSTOMGAME" + ',' + ID));
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<LobbyGame> getGamesList() {
        return gamesList;
    }

    public void setGamesList(ObservableList<LobbyGame> gamesList) {
        this.gamesList = gamesList;
    }

    public void logout() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
