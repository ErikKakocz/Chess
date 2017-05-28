package ControllerInterfaces;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Exceptions.InvalidRegistrationException;

/**
 *
 * @author User
 */
public interface RegistrationControllerInterface {

    public void persistUser(String username,String nick,String pass,String email) throws InvalidRegistrationException;
        
}
