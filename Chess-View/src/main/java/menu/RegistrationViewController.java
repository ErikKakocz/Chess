/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import ControllerInterfaces.RegistrationControllerInterface;
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
    TextField PasswordField;
    
    @FXML
    TextField PasswordAgainField;
    
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
        if(doValidation()){
            regContInt.persistUser(UserNameField.getText(), NickNameField.getText(), PasswordField.getText(), EmailField.getText());
            Alert alert=new Alert(Alert.AlertType.INFORMATION,"Registration succesful.",ButtonType.OK);
            
        }
        else{
            Alert alert=new Alert(Alert.AlertType.ERROR,"Registration unsuccesful.",ButtonType.OK);
            
        }
    }   
        
    
    public boolean doValidation(){
        UnsuccesfulValidationLabel.setVisible(true);
        
        if(!(UserNameField.getText().matches("([A-Z][a-z][0-9])+") && UserNameField.getText().length()>8))
            UnsuccesfulValidationLabel.setText("Username must be longer than 8 characters and must contain only letters and numbers.");
        else if(!(NickNameField.getText().matches("([A-Z][a-z][0-9])+") && NickNameField.getText().length()>8))
            UnsuccesfulValidationLabel.setText("Nickname must be longer than 8 characters and must contain only letters and numbers.");
        else if(!(PasswordField.getText().matches("([A-Z][a-z][0-9])+") && 
                PasswordField.getText().length()>8 ))
            UnsuccesfulValidationLabel.setText("Nickname must be longer than 8 characters and must contain only letters and numbers.");
        else if(!PasswordAgainField.getText().equals(PasswordField.getText()))
            UnsuccesfulValidationLabel.setText("Passwords must match.");
        else if(!EmailField.getText().matches("([a-z][0-9])+@[a-z]+\\.[a-z]+"))
            UnsuccesfulValidationLabel.setText("Invalid E-mail address.");
        else{
            UnsuccesfulValidationLabel.setVisible(false);
            return true;
        }
        return false;
    }
    
    public void cancelRegistration(){
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        System.out.println("asd");
        stage.close();
    }
}
