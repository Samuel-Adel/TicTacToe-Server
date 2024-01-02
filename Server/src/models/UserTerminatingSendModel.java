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
public class UserTerminatingSendModel extends JsonSendBase {

    private String player1UserName;
    private String player2UserName;
    private String userNameExiting;

    public String getPlayer1UserName() {
        return player1UserName;
    }

    public void setPlayer1UserName(String player1UserName) {
        this.player1UserName = player1UserName;
    }

    public String getPlayer2UserName() {
        return player2UserName;
    }

    public void setPlayer2UserName(String player2UserName) {
        this.player2UserName = player2UserName;
    }

    public String getUserNameExiting() {
        return userNameExiting;
    }

    public void setUserNameExiting(String userNameExiting) {
        this.userNameExiting = userNameExiting;
    }

    public int getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public void setSendModelFromRecieveModel(UserTerminatingResponse jsonRecieve) {
        player1UserName = jsonRecieve.getPlayer1UserName();
        player2UserName = jsonRecieve.getPlayer2UserName();
        userNameExiting = jsonRecieve.getUserNameExiting();
        requestType = jsonRecieve.getType();
    }
}
