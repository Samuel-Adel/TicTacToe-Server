/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import database.DataBaseManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omar
 */
public class Registration extends JsonReceiveBase{

    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public Registration() {

        this.username = null;
        this.password = null;
    }

    public Registration(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Registration registerPlayer(String userName, String password) {
        Registration player = new Registration();//3lshan a5leh b null lw tl3 mwgod
        DataBaseManager connection = new DataBaseManager();

        try {

            PreparedStatement ps1 = connection.con.prepareStatement("Select user_name from player Where user_name = ?");
            ps1.setString(1, userName);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                System.out.print("Player username is exist, please enter another userName");

            } else {

                PreparedStatement pst = connection.con.prepareStatement("INSERT INTO ROOT.PLAYER (user_name,Password,Score,Status) VALUES ( ? ,? , ? , ?)");
                pst.setString(1, userName);
                pst.setString(2, password);
                pst.setInt(3, 0);
                pst.setInt(4, 0);
                pst.executeUpdate();
                player.setUsername(userName);
                player.setPassword(password);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }

        return player;
    }

}
