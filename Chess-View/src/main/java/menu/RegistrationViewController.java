/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import ControllerInterfaces.RegistrationControllerInterface;
import Exceptions.InvalidRegistrationException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author User
 */
public class RegistrationViewController implements Initializable {

    RegistrationControllerInterface regContInt;
    
    @FXML
    Button RegisterButton;
    
    @FXML
    Button CancelButton;
    
    @FXML
    TextField UserNameField;
    
    @FXML
    TextField NickNameField;
    
    @FXML
    PasswordField PasswordField;
    
    @FXML
    PasswordField PasswordAgainField;
    
    @FXML
    TextField EmailField;
    
    @FXML
    Label UnsuccesfulValidationLabel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RegisterButton.setOnAction(new EventHandler(){
        public void handle(Event e){
            registerUser();
                
        }
        });
        CancelButton.setOnAction(new EventHandler(){
        public void handle(Event e){
            cancelRegistration();
        }
        });
    }    

    public void setupInterface(Object RegCont) {
        regContInt = (RegistrationControllerInterface) RegCont;
        
    }
    
    public void registerUser(){
        Alert alert=null;
        if(doValidation()){
            try{
                regContInt.persistUser(UserNameField.getText(), NickNameField.getText(), PasswordField.getText(), EmailField.getText());
                alert=new Alert(Alert.AlertType.INFORMATION,"Registration succesful.",ButtonType.OK);
            }catch(InvalidRegistrationException ex){
                if(ex.getMessage().equals("REGISTRATIONFAILURE,EXISTINGUSER"))
                    alert=new Alert(Alert.AlertType.ERROR,"Registration failed. Username already exists",ButtonType.OK);
                else if(ex.getMessage().equals("REGISTRATIONFAILURE,EXISTINGEMAIL"))
                    alert=new Alert(Alert.AlertType.ERROR,"Registration failed. E-mail already in use",ButtonType.OK);
            }
        }
        else{
            alert=new Alert(Alert.AlertType.ERROR,"Registration unsuccesful. Invalid fields.",ButtonType.OK);          
        }
        alert.show();
    }   
        
    
    public boolean doValidation(){
        UnsuccesfulValidationLabel.setVisible(true);
        
        if(!(UserNameField.getText().matches("(([A-Z]|[a-z]|[0-9]){8,16})") 
                && UserNameField.getText().matches(".*[A-Z]+.*")
                && UserNameField.getText().matches(".*[a-z]+.*")
                && UserNameField.getText().matches(".*[0-9]+.*")))
            UnsuccesfulValidationLabel.setText("Username must be between 8 and 16 characters and must contain only letters, capital letters and numbers.");
        else if(!(NickNameField.getText().matches("([A-Z]|[a-z]|[0-9]){8,16}")))
            UnsuccesfulValidationLabel.setText("Nickname must be between 8 and 16 characters and must contain only letters and capital letters.");
        else if(!(PasswordField.getText().matches("([A-Z]|[a-z]|[0-9]){8,}")))
            UnsuccesfulValidationLabel.setText("Password must be longer than 8 characters and must contain only letters and numbers.");
        else if(!PasswordAgainField.getText().equals(PasswordField.getText()))
            UnsuccesfulValidationLabel.setText("Passwords must match.");
        else if(!EmailField.getText().matches("([a-z]|[0-9]|\\.)+@[a-z]+\\.[a-z]{2,3}"))
            UnsuccesfulValidationLabel.setText("Invalid E-mail address.");
        else{
            UnsuccesfulValidationLabel.setVisible(false);
            return true;
        }
        return false;
    }
    
    public void cancelRegistration(){
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        
        stage.close();
    }
}
