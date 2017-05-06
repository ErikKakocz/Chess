/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Exceptions.InvalidLoginCredentialsException;


/**
 *
 * @author User
 */
public class ApplicationController implements ControllerInterfaces.ApplicationControllerInterface{

    Controller controller;
    
    public ApplicationController(){
    
    }
    
    public ApplicationController(Controller control){
        controller=control;
    }
    
    @Override
    public void QuitGame() {
        controller.QuitGame();
    }
    
    @Override
    public void OpenRegistration(String UName, String Pass){
        controller.OpenRegistrationWindow(UName, Pass);
    }

    public void login(String username,String password) throws InvalidLoginCredentialsException {
        controller.login(username, password);
    }
}
