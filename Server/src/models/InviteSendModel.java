/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author allam
 */
public class InviteSendModel extends JsonSendBase {
    
    String recieverUserName;

    public void setRecieverUserName(String reciever) {
        this.recieverUserName = reciever;
    }

    public String getRecieverUserName() {
        return recieverUserName;
    }
     
}
