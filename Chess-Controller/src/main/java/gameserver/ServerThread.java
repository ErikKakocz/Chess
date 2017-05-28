/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import Persistence.User;
import controller.Persistence.PersistenceController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author User
 */
class ServerThread implements Runnable, Comparable {

    PersistenceController persistenceController;

    Socket socket = null;
    Server server;
    User user;
    GameplayController gameplayController;
    boolean waitingOnMatch;
    
    public GameplayController getGameplayController() {
        return gameplayController;
    }

    public void setGameplayController(GameplayController gameplayController) {
        this.gameplayController = gameplayController;
    }

    public ServerThread() {
        System.out.println("Serverthread created");
    }

    public ServerThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        System.out.println("Serverthread created");
        
        
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        ObjectInputStream inStream = null;
        ObjectOutputStream outStream = null;

        if (socket != null) {
            try {

                inStream = new ObjectInputStream(socket.getInputStream());
                outStream = new ObjectOutputStream(socket.getOutputStream());
                outStream.flush();

                while (!socket.isClosed()) {
                    outStream.writeObject(processInput((NetworkMessage) inStream.readObject()));
                }

            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private NetworkMessage processInput(NetworkMessage input) throws InterruptedException {
        NetworkMessage answer = null;
        System.out.println("Incoming request:" + input.getMessage());
        if (input.getMessage().startsWith("LOGIN")) {
            String uname = input.getMessage().split(",")[1];
            String pass = input.getMessage().split(",")[2];
            User userToLogin = persistenceController.getUserByUserName(uname);
            if (userToLogin != null && BCrypt.checkpw(pass, userToLogin.getPassword())) {
                answer = new NetworkMessage("LOGINSUCCES", userToLogin);
                this.user = userToLogin;
            } else {
                answer = new NetworkMessage("LOGINFAILURE");
            }
        } else if (input.getMessage().startsWith("REGISTER")) {
            User userToPersist = (User) input.getAttachment();
            if (persistenceController.getUserByUserName(userToPersist.getUsername()) != null) {
                answer = new NetworkMessage("REGISTRATIONFAILURE,EXISTINGUSER");
            } else if (persistenceController.getUserByEmail(userToPersist.getEmail()) != null) {
                answer = new NetworkMessage("REGISTRATIONFAILURE,EXISTINGEMAIL");
            } else {
                Logger.getLogger(ServerThread.class.getName()).log(Level.INFO, "Persisting:" + userToPersist.toString());
                userToPersist.setPassword(BCrypt.hashpw(userToPersist.getPassword(), BCrypt.gensalt()));
                persistenceController.persistUser(userToPersist);
                answer = new NetworkMessage("REGISTRATIONSUCCES");
            }
        } else if(input.getMessage().startsWith("JOINCUSTOMGAME")){
        
        }else if(input.getMessage().startsWith("JOINMATCHMAKING")){
            waitingOnMatch=true;
            server.JoinMatchmaking(this);
            while(waitingOnMatch)
                Thread.sleep(1000);
            answer=new NetworkMessage("MATCHFOUND");
            
        }else if(input.getMessage().startsWith("CREATECUSTOMGAME")){
            
        }
            
            
        return answer;
    }

    public int compareTo(Object o) {
        return (this.user.getELO() - ((ServerThread) o).user.getELO());
    }

    public PersistenceController getPersistenceController() {
        return persistenceController;
    }

    public void setPersistenceController(PersistenceController persistenceController) {
        this.persistenceController = persistenceController;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean isWaitingOnMatch() {
        return waitingOnMatch;
    }

    public void setWaitingOnMatch(boolean waitingOnMatch) {
        this.waitingOnMatch = waitingOnMatch;
    }

    

}
