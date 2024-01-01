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
import models.WinnerModelResponse;

/**
 *
 * @author Sasa Adel
 */
public class ScoreUpdateHelper {

    private final WinnerModelResponse winnerModelResponse;
    private final DataBaseManager dataBaseManager;
    private PreparedStatement updateStatement;

    public ScoreUpdateHelper(WinnerModelResponse winnerModelResponse, DataBaseManager dataBaseManager) {
        this.winnerModelResponse = winnerModelResponse;
        this.dataBaseManager = dataBaseManager;
    }

    public void updateUserScore() throws SQLException {
        updateStatement = dataBaseManager.con.prepareStatement("UPDATE PLAYER SET Score = Score+10 WHERE user_name = ?");
        updateStatement.setString(1, winnerModelResponse.getWinnerUserName());
        int rowsAffected = updateStatement.executeUpdate();
        System.err.println("Rows Affected " + rowsAffected);

    }
}
