/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerInterfaces;

import Exceptions.InvalidLoginCredentialsException;

/**
 *
 * @author User
 */
public interface ApplicationControllerInterface {
    
    abstract void QuitGame();

    abstract void OpenRegistration(String UName,String Pass);

    abstract void login(String username,String password) throws InvalidLoginCredentialsException;
}
