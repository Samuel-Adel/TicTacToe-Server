/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import database.DataBaseManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.LoginResponseModel;
import Entity.Player;
import database.DataBaseManager;

/**
 *
 * @author Sasa Adel
 */
public class LoginDB {

    public LoginResponseModel loginModel;
    public DataBaseManager dataBaseManager;
    private PreparedStatement loginStatement;
    private ResultSet loginStatmentResult;
    private String message;
    private Player player;

    public LoginDB(LoginResponseModel loginModel, DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
        this.loginModel = loginModel;
        player=new Player();
    }

    public String userLogin() {
        try {
            if (checkUserName()) {
                if (checkUserPassword()) {
                    message = "Loggedin Successfully";
                    return "Loggedin Successfully";
                } else {
                    message = "Inorrect Password";
                    return "Inorrect Password";
                }
            } else {
                message = "User Name Not Found";
                return "User Name Not Found";
            }
        } catch (SQLException ex) {
            message = ex.toString();
            return ex.toString();
        }
    }

    private boolean checkUserName() throws SQLException {
        loginStatement = dataBaseManager.con.prepareStatement("Select user_name from PLAYER Where user_name = ?");
        loginStatement.setString(1, loginModel.getUserName());
        loginStatmentResult = loginStatement.executeQuery();
        if (loginStatmentResult.next()) {           
            return true;
        } else {
            return false;
        }
    }

    private boolean checkUserPassword() throws SQLException {
        loginStatement = dataBaseManager.con.prepareStatement("Select * from player Where password = ?");
        loginStatement.setString(1, loginModel.getPassword());
        loginStatmentResult = loginStatement.executeQuery();
        if (loginStatmentResult.next()) {
            player.setId(loginStatmentResult.getInt("PLAYER_ID"));
            player.setStatus(loginStatmentResult.getInt("Status"));
            player.setScore(loginStatmentResult.getInt("Score"));
            player.setUserName(loginStatmentResult.getString("user_name"));
            return true;
        } else {
            return false;
        }
    }

    public int checkLoginOperation() {
        switch (message) {
            case "Loggedin Successfully":
                return 1;
            default:
                return 0;
        }

    }

    public Player getPlayerData() {
        return player;
    }
}
