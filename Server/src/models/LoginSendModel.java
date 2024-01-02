/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import Entity.Player;
import helpers.RequestTypes;

/**
 *
 * @author Sasa Adel
 */
public class LoginSendModel extends JsonSendBase {

    private int id;
    private String userName;
    private int score;
    private int playerStatus;

    // Getter and Setter for 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPlayerData(Player player, JsonSendBase jsonSendBase) {
        id = player.getId();
        userName = player.getUserName();
        playerStatus = player.getStatus();
        score = player.getScore();
        this.requestStatus = jsonSendBase.getStatus();
        this.requestMessage = jsonSendBase.getMessge();
        this.requestType=jsonSendBase.getType();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int setplayerStatus() {
        return playerStatus;
    }

    public void getplayerStatus(int playerStatus) {
        this.playerStatus = playerStatus;
    }
}
