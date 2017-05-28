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
        AppCont = new ApplicationController(socket,this);

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


}