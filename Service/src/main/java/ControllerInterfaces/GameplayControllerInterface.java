package ControllerInterfaces;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author User
 */
public interface GameplayControllerInterface {
    
    
    
    abstract void giveUp();
    
    abstract void askForValidation(int fromRow, int fromCol, int toRow, int toCol);
    
    abstract void movePiece(int fromRow, int fromCol, int toRow, int toCol);
   
}
