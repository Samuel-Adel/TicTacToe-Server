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
    
    String senderUserName;
    String receieverUserName;
    

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    public String getReceieverUserName() {
        return receieverUserName;
    }

    public void setReceieverUserName(String receieverUserName) {
        this.receieverUserName = receieverUserName;
    }

   
}
