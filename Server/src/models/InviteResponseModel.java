/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import helpers.RequestTypes;

/**
 *
 * @author allam
 */
public class InviteResponseModel extends JsonSendBase{
   
    private int status;
    String receiverUserName;
    String senderUserName;

    public String getReceiverUserName() {
        return receiverUserName;
    }

    public void setReceiverUserName(String ReceiverUserName) {
        this.receiverUserName = ReceiverUserName;
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

   

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
