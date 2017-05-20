package controller;

import Exceptions.InvalidLoginCredentialsException;
import Exceptions.InvalidRegistrationException;
import Persistence.User;
import gameserver.NetworkMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.Properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import menu.MenuViewController;
import menu.RegistrationViewController;

//import menu.RegistrationController;
public class Controller extends Application {

    private Socket socket = null;
    static Stage stage;
    static Properties props;
    ApplicationController AppCont;
    User user;

    public Controller() {

    }

    @Override
    public void start(Stage arg) throws Exception {

        stage = arg;

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(MenuViewController.class.getResource("/fxml/Menu.fxml"));

        stage.setTitle("Chessmaster");
        AppCont = new ApplicationController(this);

        AnchorPane ancpane = null;
        try {
            ancpane = (AnchorPane) loader.load();

        } catch (Exception e) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
        }

        Scene scene = new Scene(ancpane);

        stage.setScene(scene);

        MenuViewController controller = loader.<MenuViewController>getController();
        controller.setupInterface(AppCont);
        stage.show();

    }

    public void QuitGame() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            Platform.exit();
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void OpenRegistrationWindow(String Uname, String Pass) {
        Stage regStage = new Stage();
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(menu.RegistrationViewController.class.getResource("/fxml/Registration.fxml"));

        regStage.setTitle("Registration");
        RegistrationController RegCont = new RegistrationController(this);

        AnchorPane ancpane = null;
        try {
            ancpane = (AnchorPane) loader.load();

        } catch (Exception e) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
        }
        if (!Uname.isEmpty()) {
            ((TextField) ancpane.lookup("#UserNameField")).setText(Uname);
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

    public void login(String username, String password) throws InvalidLoginCredentialsException {
        try {
            socket = new Socket(Inet4Address.getLocalHost(), 5555); //TODO:Lecserélni 
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            outStream.writeObject(new NetworkMessage("LOGIN," + username + ',' + password));
            user = (User) inStream.readObject();
            if (user != null) //todo bejelentkeztetés
            {
                Stage lobbyStage = new Stage();
                FXMLLoader loader = new FXMLLoader();

                loader.setLocation(RegistrationController.class.getResource("/fxml/Lobby.fxml"));

                lobbyStage.setTitle("ChessMaster");
                LobbyController gameCont = new LobbyController(this);

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

    public void sendMatchmakingJoinRequest() {
        try {
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            outStream.writeObject(new NetworkMessage("JOINMATCHMAKING"));
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void sendCreateCustomGameRequest() {
        try {
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            outStream.writeObject(new NetworkMessage("CREATECUSTOMGAME"));
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void sendJoinGameRequest(int ID) {
        try {
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            outStream.writeObject(new NetworkMessage("JOINCUSTOMGAME" + ',' + ID));
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void sendRegistrationRequest(User user) throws InvalidRegistrationException {
        try {
            socket = new Socket(Inet4Address.getLocalHost(), 5555); //TODO:Lecserélni 

            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());

            outStream.writeObject(new NetworkMessage("REGISTER", user));
            NetworkMessage result=(NetworkMessage)inStream.readObject();
            if(result.getMessage().equals("REGISTRATIONFAILURE,EXISTINGUSER")){    
                InvalidRegistrationException ex=new InvalidRegistrationException();
                ex.setMessage("Existing User");
                throw ex;
            }
            if(result.getMessage().equals("REGISTRATIONFAILURE,EXISTINGEMAIL")){    
                InvalidRegistrationException ex=new InvalidRegistrationException();
                ex.setMessage("Existing Email");
                throw ex;
            }
                
                
        } catch(IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
