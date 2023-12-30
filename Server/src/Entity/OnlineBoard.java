/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import models.JsonSendBase;


/**
 *
 * @author Aya
 */
public class OnlineBoard extends JsonSendBase{
    String senderUserName;
    String ReceiverUserName;

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    public String getReceiverUserName() {
        return ReceiverUserName;
    }

    public void setReceiverUserName(String ReceiverUserName) {
        this.ReceiverUserName = ReceiverUserName;
    }
    String currentTurn;
  


    public OnlineBoard(String senderUN, String ReceiverUN) {
        this.senderUserName = senderUN;
        this.ReceiverUserName = ReceiverUN;
        //this.currentTurn = currentTurn;
    }

    public void setSenderUN(String senderUN) {
        this.senderUserName = senderUN;
    }

    public void setReceiverUN(String ReceiverUN) {
        this.ReceiverUserName = ReceiverUN;
    }

    public void setCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
    }

    public String getSenderUN() {
        return senderUserName;
    }

    public String getReceiverUN() {
        return ReceiverUserName;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }
    
    
    
}
