/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Exceptions.InvalidLoginCredentialsException;
import Persistence.User;
import gameserver.NetworkMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import menu.RegistrationViewController;


/**
 *
 * @author User
 */
public class ApplicationController implements ControllerInterfaces.ApplicationControllerInterface{

    Socket socket;
    Controller controller;
    private User user;
    
    public ApplicationController(){
    
    }
    
    public ApplicationController(Socket sock,Controller control){
        socket=sock;
        controller=control;
    }
    
    @Override
    public void QuitGame() {
        controller.QuitGame();
    }
    
    @Override
    public void OpenRegistration(String UName, String Pass){
        Stage regStage = new Stage();
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(menu.RegistrationViewController.class.getResource("/fxml/Registration.fxml"));

        regStage.setTitle("Registration");
        RegistrationController RegCont = new RegistrationController();

        AnchorPane ancpane = null;
        try {
            ancpane = (AnchorPane) loader.load();

        } catch (Exception e) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
        }
        if (!UName.isEmpty()) {
            ((TextField) ancpane.lookup("#UserNameField")).setText(UName);
        }

        if (!Pass.isEmpty()) {
            ((TextField) ancpane.lookup("#PasswordField")).setText(Pass);
            ((TextField) ancpane.lookup("#PasswordAgainField")).setText(Pass);
        }

        Scene scene = new Scene(ancpane);
        regStage.setScene(scene);
        RegistrationViewController controller = loader.<RegistrationViewController>getController();
        controller.setupInterface(RegCont);
        regStage.show();
    }

    public void login(String username,String password) throws InvalidLoginCredentialsException {
        try {
            Logger.getLogger(Controller.class.getName()).log(Level.INFO, "Attempting login");
            socket = new Socket(Inet4Address.getLocalHost(), 5555); 
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            outStream.flush();
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

            outStream.writeObject(new NetworkMessage("LOGIN," + username + ',' + password));
            NetworkMessage m = (NetworkMessage) inStream.readObject();
            Logger.getLogger(Controller.class.getName()).log(Level.INFO, "user:"+m.getMessage());
            if (m.getMessage().equals("LOGINSUCCES")) //todo bejelentkeztetés
            {
                user=(User)m.getAttachment();
                Stage lobbyStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                

                loader.setLocation(RegistrationController.class.getResource("/fxml/Lobby.fxml"));

                lobbyStage.setTitle("ChessMaster");
                LobbyController gameCont = new LobbyController(socket,outStream,inStream);

                AnchorPane ancpane = null;
                try {
                    ancpane = (AnchorPane) loader.load();

                } catch (Exception e) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
                }

                Scene scene = new Scene(ancpane);
                lobbyStage.setScene(scene);
                lobby.LobbyViewController controller = loader.<lobby.LobbyViewController>getController();
                controller.setUser(user);
                controller.setupInterface(gameCont);

                lobbyStage.show();
            } else {
                
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
                throw new InvalidLoginCredentialsException();
                
            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
