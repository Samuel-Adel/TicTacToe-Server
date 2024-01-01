/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import helpers.RequestTypes;
/**
 *
 * @author Hadi
 */
public class OnlineGameModel extends JsonSendBase {

    private String player1UserName;
    private String player2UserName;
    private String currentPlayerUserName;
    private int row;
    private int col;
    private char currentPlayerMark;

    public OnlineGameModel() {
        super.requestType = RequestTypes.OnlineGame.name();

    }

    public char getCurrentPlayerMark() {
        return currentPlayerMark;
    }

    public void setCurrentPlayerMark(char currentPlayerMark) {
        this.currentPlayerMark = currentPlayerMark;
    }

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

    public String getCurrentPlayerUserName() {
        return currentPlayerUserName;
    }

    public void setCurrentPlayerUserName(String currentPlayerUserName) {
        this.currentPlayerUserName = currentPlayerUserName;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String getType() {
        return requestType;
    }
    
    @Override
    public void setType(String type) {
        this.requestType = type;
    }
}
