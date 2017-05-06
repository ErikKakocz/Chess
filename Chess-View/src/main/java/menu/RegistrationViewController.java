/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import ControllerInterfaces.RegistrationControllerInterface;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    public void setupInterface(Object RegCont) {
        regContInt = (RegistrationControllerInterface) RegCont;
        
    }
    
    public void registerUser(){
        regContInt.persistUser(UserNameField.getText(), NickNameField.getText(), PasswordField.getText(), EmailField.getText());
    
    }
    
    public void cancelRegistration(){
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }
}
