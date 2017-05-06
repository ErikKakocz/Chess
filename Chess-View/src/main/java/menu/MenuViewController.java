package menu;

import ControllerInterfaces.ApplicationControllerInterface;
import Exceptions.InvalidLoginCredentialsException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;



public class MenuViewController {
    
    Properties props;
    ApplicationControllerInterface AppContInterface;
    
    @FXML
    Text TitleText;
    
    @FXML
    Button QuitButton;

    @FXML
    Button LoginButton;
    
    @FXML
    Button SignUpButton;
	
    @FXML
    TextField UserNameField;
    
    @FXML
    TextField PassField;
    
    @FXML
    Label UserNameLabel;
    
    @FXML
    Label PasswordLabel;
    
    @FXML
    Label LanguageLabel;
    
    @FXML
    ComboBox LanguageComboBox;
    
    @FXML
    Label ErrorLabel;
    
    public MenuViewController(){
        //System.out.println("Still loading");
        
        
    }
    
    
    
    public void setupInterface(Object ACI){
        AppContInterface=(ApplicationControllerInterface)ACI;
        
        ObservableList<String> ObList=FXCollections.observableArrayList("English","Magyar","Deutsch");
        SortedList ls=new SortedList(ObList);
        LanguageComboBox.setItems(ls);
        QuitButton.setOnAction(new EventHandler() {
            public void handle(Event event) {
                AppContInterface.QuitGame();
            }
        });
        SignUpButton.setOnAction(new EventHandler() {
            public void handle(Event event) {
                
                AppContInterface.OpenRegistration(UserNameField.getText(),PassField.getText());
            }
        });
        LanguageComboBox.setOnAction(new EventHandler() {
            public void handle(Event event) {
                
                changeLanguage();
            }
        });
        LoginButton.setOnAction(new EventHandler() {
            public void handle(Event event) {
                
                try {
                    AppContInterface.login(UserNameField.getText(), PassField.getText());
                } catch (InvalidLoginCredentialsException ex) {
                    Logger.getLogger(MenuViewController.class.getName()).log(Level.SEVERE, null, ex);
                    ErrorLabel.setText("Hibaüzi");
                }
            }
        });
    
    }
    
    
    void changeLanguage(){
        FileInputStream inStream=null;
        String filename="Text_hu.properties";
        String language=LanguageComboBox.getValue().toString();
        System.out.println(language);
        if(language.equals("English")){
            filename="Text_en.properties";
        }else if(language.equals("Deutsch")){
            filename="Text_ger.properties";
        }
        try {
        inStream=new FileInputStream(new File(this.getClass().getClassLoader().getResource("LanguageFiles/"+filename).toString()));
        
        
            props.load(inStream);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());    
            Logger.getLogger(MenuViewController.class.getName()).log(Level.SEVERE, null, ex);
                
        }
        QuitButton.setText(props.getProperty("quit"));
        SignUpButton.setText(props.getProperty("signup"));
        LanguageLabel.setText(props.getProperty("languages"));
        LanguageComboBox.setPromptText(props.getProperty("languageprompt"));
        LoginButton.setText(props.getProperty("login"));
        UserNameLabel.setText(props.getProperty("username"));
        PasswordLabel.setText(props.getProperty("password"));
    
    }
}
