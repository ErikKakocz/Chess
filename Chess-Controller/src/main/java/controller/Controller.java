package controller;

import Exceptions.InvalidLoginCredentialsException;
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

import table.Table;


public class Controller extends Application{

	static Table table;
	
	static Stage stage;
	static Properties props;
        ApplicationController AppCont;
	
	public Controller(){
		
	}
	
	
	
	@Override
	public void start(Stage arg) throws Exception {
            
            stage = arg;
		
                FXMLLoader loader=new FXMLLoader();
		
                loader.setLocation(MenuViewController.class.getResource("/fxml/Menu.fxml"));
		
                stage.setTitle("Chessmaster");
                AppCont=new ApplicationController(this);
                
                
		AnchorPane ancpane = null;
                try{
                    ancpane=(AnchorPane)loader.load();
                    
                }catch(Exception e){
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
                }
                
                Scene scene=new Scene(ancpane);
		
                stage.setScene(scene);
                
                MenuViewController controller = loader.<MenuViewController>getController();
                controller.setupInterface(AppCont);
		stage.show();
		
	}
        
        public void QuitGame(){
            try {
                Platform.exit();
            } catch (Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
        public void OpenRegistrationWindow(String Uname,String Pass){	
                Stage regStage=new Stage();
                FXMLLoader loader=new FXMLLoader();
		
                
                loader.setLocation(menu.RegistrationViewController.class.getResource("/fxml/Registration.fxml"));
		
                regStage.setTitle("Registration");
                RegistrationController RegCont=new RegistrationController(this);
                
                
		AnchorPane ancpane = null;
                try{
                    ancpane=(AnchorPane)loader.load();
                    
                }catch(Exception e){
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
                }
                if(!Uname.isEmpty())
                    ((TextField)ancpane.lookup("#UserNameField")).setText(Uname);
               
                if(!Pass.isEmpty()){
                    ((TextField)ancpane.lookup("#PasswordField")).setText(Pass);
                    ((TextField)ancpane.lookup("#PasswordAgainField")).setText(Pass);
                }
                
                
                Scene scene=new Scene(ancpane);
		regStage.setScene(scene);
                RegistrationViewController controller = loader.<RegistrationViewController>getController();
                controller.setupInterface(RegCont);
		regStage.show();
        
        }
        
        public void login(String username,String password) throws InvalidLoginCredentialsException{
            Stage gameStage;    
            
            if(true) //todo bejelentkeztetés
            {
                gameStage=new Stage();
                FXMLLoader loader=new FXMLLoader();
		
                loader.setLocation(RegistrationController.class.getResource("/fxml/Registration.fxml"));
		
                gameStage.setTitle("ChessMaster");
                GameplayController gameCont=new GameplayController();
                
                
		AnchorPane ancpane = null;
                try{
                    ancpane=(AnchorPane)loader.load();
                    
                }catch(Exception e){
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
                }
                
                Scene scene=new Scene(ancpane);
		gameStage.setScene(scene);
                menu.RegistrationViewController controller = loader.<menu.RegistrationViewController>getController();
                controller.setupInterface(gameCont);
		gameStage.show();
            }else{
                throw new InvalidLoginCredentialsException();
            }

        }

    private static class RegistrationController {

        public RegistrationController() {
        }

        private RegistrationController(Controller aThis) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
