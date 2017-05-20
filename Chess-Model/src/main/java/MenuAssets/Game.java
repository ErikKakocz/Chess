/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuAssets;

import Persistence.User;

/**
 *
 * @author User
 */



public class Game {
    
    static int[] EloDiff={100,125,150,175,200,250,300,350,400,500,600,700,1000};
    
    User player1,player2;
    int MaxELODiff=100;
    
    
    public Game() {
        
    }

    public User getPlayer1() {
        return player1;
    }

    public void setPlayer1(User player1) {
        this.player1 = player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
    }

    public int getMaxELODiff() {
        return MaxELODiff;
    }

    public void setMaxELODiff(int MaxELODiff) {
        this.MaxELODiff = MaxELODiff;
    }
    
}
