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
public class InviteSendModel {

    
    private String senderUserName;
    private String recieverUserName;
    private RequestTypes ResponseType;

    public RequestTypes getSendRequestType() {
        return ResponseType;
    }

    public void setSendRequestType(RequestTypes SendRequestType) {
        this.ResponseType = SendRequestType;
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }
    

    public void setRecieverUserName(String reciever) {
        this.recieverUserName = reciever;
    }

    public String getRecieverUserName() {
        return recieverUserName;
    }

}
