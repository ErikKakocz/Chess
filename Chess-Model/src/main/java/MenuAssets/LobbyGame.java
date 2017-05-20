/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuAssets;

/**
 *
 * @author User
 */
public class LobbyGame {
        
    String userName;
    int ELO;
    long gameID;

    public LobbyGame(String userName, int ELO, long gameID) {
        this.userName = userName;
        this.ELO = ELO;
        this.gameID = gameID;
    }

    public LobbyGame() {
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getELO() {
        return ELO;
    }

    public void setELO(int ELO) {
        this.ELO = ELO;
    }

    public long getGameID() {
        return gameID;
    }

    public void setGameID(long gameID) {
        this.gameID = gameID;
    }
    
    
}
