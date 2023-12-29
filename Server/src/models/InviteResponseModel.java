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
public class InviteResponseModel extends JsonReceiveBase {
    
    private String senderUserName;
    private String receiverUserName;
    private final RequestTypes requestType = RequestTypes.Invite;
   


    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    public String getReceieverUserName() {
        return receiverUserName;
    }

    public void setReceieverUserName(String receieverUserName) {
        this.receiverUserName = receieverUserName;
    }

   
}
