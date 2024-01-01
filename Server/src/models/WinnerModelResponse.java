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
public class WinnerModelResponse extends JsonReceiveBase{
    private String winnerUserName;
    public String getWinnerUserName() {
        return winnerUserName;
    }

    public void setWinnerUserName(String winnerUserName) {
        this.winnerUserName = winnerUserName;
    }

    
}
