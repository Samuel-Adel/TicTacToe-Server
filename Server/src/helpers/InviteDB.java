/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import Entity.Player;
import database.DataBaseManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import models.InviteResponseModel;

/**
 *
 * @author allam
 */
//public class InviteDB {
//    
//    private InviteResponseModel inviteResponseModel;
//    public DataBaseManager dataBasemanger;
//    private PreparedStatement inviteStatment;
//    
//   private Player player;
//   
//   public InviteDB(DataBaseManager dataBaseManager){
//       this.dataBasemanger = dataBaseManager;
//       player = new Player();
//   }
//   
//   
//   public int checkStatus() throws SQLException{
//       
//   inviteStatment = dataBasemanger.con.prepareStatement("SELECT status from PLAYER WHERE status = ?");
//   inviteStatment.setString(1, );
//   
//   
//   }
//    
//}
