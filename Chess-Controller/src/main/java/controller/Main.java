/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gameserver.Server;
import javafx.application.Application;

/**
 *
 * @author User
 */
public class Main {
    
    public static void main(String[] args) {
            System.out.println(args.length);
            if(args.length>0 && args[0].equals("server")){
                Server s=new Server();
                s.run();
            }else{
                Application.launch(Controller.class,args);
            }
                
                
	}
    
}
