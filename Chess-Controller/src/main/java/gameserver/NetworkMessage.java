/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

/**
 *
 * @author User
 */
public class NetworkMessage {
    String message;
    Object attachment;
    
    public NetworkMessage(){}

    public NetworkMessage(String message) {
        this.message = message;
    }

    public NetworkMessage(String message, Object attachment) {
        this.message = message;
        this.attachment = attachment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }
    
    
    
}
