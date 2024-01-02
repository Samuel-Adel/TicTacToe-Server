/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import Entity.Player;
import database.DataBaseManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import java.util.logging.Logger;

/**
 *
 * @author Aya
 */
public class ListDB {
    public DataBaseManager dataBaseManager;
    private PreparedStatement listStatement;
    private ResultSet listStatmentResult;
    
    public ListDB() {
        dataBaseManager=new DataBaseManager();
    }
 
    public ArrayList<Player> getActivePlayersFromDatabase(){
        
       ArrayList<Player> activePlayers = new ArrayList<>();
         try {
             listStatement = dataBaseManager.con.prepareStatement("SELECT user_name, score FROM player WHERE status = 1" ,
             ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             listStatmentResult = listStatement.executeQuery();

            while (listStatmentResult.next()) {
                String userName = listStatmentResult.getString("user_name");
                int score = listStatmentResult.getInt("score");
                activePlayers.add(new Player(userName, score));
            }

            System.out.println("Server Received Data of Active Players From DB");
        
    } catch (SQLException ex) {
        Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
            try{
               if(listStatement != null){
                       listStatmentResult.close();
               }
               if(listStatement != null){
                   listStatement.close();
               }
            }catch (SQLException ex) {
               Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
    return activePlayers;
    }
    
}
