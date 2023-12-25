/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
/**
 *
 * @author Sasa Adel
 */
public class JsonSendBase {
    int requestStatus; // 1 if there is a user , 2 if the database throw an un expected exception // 0 there is no user
    String requestType;
    String requestMessage;

    public int getStatus() {
        return requestStatus;
    }

    public void setStatus(int status) {
        this.requestStatus = status;
    }

    public String getType() {
        return requestType;
    }

    public void setType(String mode) {
        this.requestType = mode;
    }

    public String getMessge() {
        return requestMessage;
    }

    public void setMessge(String messge) {
        this.requestMessage = messge;
    }

   

}
