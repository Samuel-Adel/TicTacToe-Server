/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import database.DataBaseManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.LoginModel;

/**
 *
 * @author Sasa Adel
 */
public class LoginDB {

    public LoginModel loginModel;
    public DataBaseManager dataBaseManager;
    private PreparedStatement loginStatement;
    private ResultSet loginStatmentResult;
    private String message;

    public LoginDB(LoginModel loginModel, DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
        this.loginModel = loginModel;
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
            message=ex.toString();
            return ex.toString();
        }
    }

    private boolean checkUserName() throws SQLException {
        loginStatement = dataBaseManager.con.prepareStatement("Select user_name from player Where user_name = ?");
        loginStatement.setString(1, loginModel.getUserName());
        loginStatmentResult = loginStatement.executeQuery();
        if (loginStatmentResult.next()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkUserPassword() throws SQLException {
        loginStatement = dataBaseManager.con.prepareStatement("Select user_name from player Where password = ?");
        loginStatement.setString(1, loginModel.getPassword());
        loginStatmentResult = loginStatement.executeQuery();
        if (loginStatmentResult.next()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkLoginOperation() {
        switch (message) {
            case "Loggedin Successfully":
                return true;
            case "Inorrect Password":
                return false;
            case "User Name Not Found":
                return false;
            default:
                return false;
        }

    }
}
