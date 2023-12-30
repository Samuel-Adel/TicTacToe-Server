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
public class OnlineBoard extends JsonSendBase {

    String senderUserName;
    String receiverUserName;
    String currentTurn;

    public OnlineBoard(String senderUN, String ReceiverUN) {
        this.senderUserName = senderUN;
        this.receiverUserName = ReceiverUN;
        //this.currentTurn = currentTurn;
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    public String getReceiverUserName() {
        return receiverUserName;
    }

    public void setReceiverUserName(String ReceiverUserName) {
        this.receiverUserName = ReceiverUserName;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
    }

}
