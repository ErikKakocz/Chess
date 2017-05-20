/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author User
 */
public class InvalidRegistrationException extends Throwable{
    
    private String Message;
    
    public void setMessage(String message){
        Message=message;
    }
    
    public String getMessage(){
        return Message;
    }
    
}
