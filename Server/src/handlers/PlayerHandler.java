/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

/**
 * @author Sasa Adel
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Entity.Player;
import helpers.LoginDB;
import com.google.gson.Gson;
import database.DataBaseManager;
import helpers.ListDB;
import helpers.RequestTypes;
import helpers.ScoreUpdateHelper;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.ExitModel;

import models.InviteResponseModel;
import models.InviteSendModel;
import models.JsonReceiveBase;
import models.JsonSendBase;
import models.LoginResponseModel;
import models.LoginSendModel;
import models.LogoutModel;
import models.OnlineBoard;
import models.OnlineGameModel;
import models.Registration;
import models.UserTerminatingResponse;
import models.UserTerminatingSendModel;
import models.WinnerModelResponse;
//import org.json.JSONObject;

/**
 * @author Sasa Adel
 */
public class PlayerHandler extends Thread {

    static private DataBaseManager dbManager;
    static int numberOfClients = 0;
    private DataInputStream ear;
    private PrintStream mouth;
    private Socket currentSocket;
    private String clientMsg;
    private JsonReceiveBase jsonRecieveBase;
    private String userName;

    private JsonSendBase jsonSendBase;

    static Vector<PlayerHandler> clientsVector // what is the difference
            = new Vector<PlayerHandler>();

    public PlayerHandler(Socket currentSocketParameter) {
        dbManager = new DataBaseManager();
        numberOfClients++;
        try {
            jsonSendBase = new JsonSendBase();
            currentSocket = currentSocketParameter;
            ear = new DataInputStream(currentSocketParameter.getInputStream());
            mouth = new PrintStream(currentSocketParameter.getOutputStream());
            PlayerHandler.clientsVector.add(this);
            start();
        } catch (IOException ex) {
            Logger.getLogger(PlayerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        try {

            while (ear != null && currentSocket.isClosed() == false && (clientMsg = ear.readLine()) != null) {
                handleOperation(clientMsg);
                System.err.println("Client Message " + clientMsg);

            }
        } catch (SocketException ex) {
            System.out.println(ex.getMessage() + "\n");
            try {
                currentSocket.close();
                ear.close();
                mouth.close();
            } catch (IOException ex1) {
                Logger.getLogger(PlayerHandler.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (IOException ex) {
            System.out.println("Can't IO");
        }

    }

    void sendMessageToAll(String msg) {
        clientsVector.forEach((ch) -> {
            ch.mouth.println(msg);
        });
    }

    //Method to broadcast the updated list of active players to all clients
    private void broadcastActivePlayers() {
        ListDB list = new ListDB();
        ArrayList<Player> playerList = list.getActivePlayersFromDatabase();
        //Remove this userNmae from the List
        //    playerList.removeIf(player -> player.getUserName().equals(clientMsg)); 
        // Convert the playerList to JSON using JsonWrapper
        jsonSendBase.setStatus(1);
        jsonSendBase.setMessge("Send Available Players Successfully");
        jsonSendBase.setType(RequestTypes.AvailPlayers.name());
        System.out.println(jsonSendBase.getType() + " " + jsonSendBase.getMessge() + " " + jsonSendBase.getStatus());
        String jsonPlayers = JsonWrapper.toJson(playerList);

        // Broadcast the updated list of players to all connected clients
        sendMessageToAll(jsonPlayers);
        System.out.println("Json Format For PlayerList: " + jsonPlayers);

    }

    private void handleOperation(String clientMessage) {
        jsonRecieveBase = JsonWrapper.fromJson(clientMsg, JsonReceiveBase.class);
        if (jsonRecieveBase.getType().equals(RequestTypes.Login.name())) {
            jsonSendBase.setType(RequestTypes.Login.name());
            LoginSendModel logineSendModel = new LoginSendModel();
            String jsonSend;
            LoginDB loginDB;
            LoginResponseModel loginResponseModel;

            loginResponseModel = JsonWrapper.fromJson(clientMsg, LoginResponseModel.class);
            System.out.println(loginResponseModel);
            System.out.println(loginResponseModel.getUserName());
            loginDB = new LoginDB(loginResponseModel, dbManager);
            jsonSendBase.setMessge(loginDB.userLogin());
            jsonSendBase.setStatus(loginDB.checkLoginOperation());

            if (jsonSendBase.getStatus() == 1) {
                this.userName = loginResponseModel.getUserName();
                System.out.println("the vector userName" + userName);
                logineSendModel.setPlayerData(loginDB.getPlayerData(), jsonSendBase);
                jsonSend = JsonWrapper.toJson(logineSendModel);
                System.out.println(jsonSend);
                mouth.println(jsonSend);
                sendMessageToAll(jsonSend);
                DataBaseManager connection = dbManager;
                try {
                    PreparedStatement preparedStatementUpdatePlayerStatus = connection.con.prepareStatement("UPDATE player SET status = 1 WHERE user_name = ?");
                    preparedStatementUpdatePlayerStatus.setString(1, userName);
                    preparedStatementUpdatePlayerStatus.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(PlayerHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                jsonSend = JsonWrapper.toJson(jsonSendBase);
                System.out.println(jsonSend);
                mouth.println(jsonSend);
            }
        } else if (jsonRecieveBase.getType().equals(RequestTypes.Register.name())) {
            String jsonSend;
            System.out.println("Test Server:Omar :" + jsonRecieveBase);
            jsonSendBase.setType(RequestTypes.Register.name());
            Gson gson = new Gson();
            Registration registrationData = JsonWrapper.fromJson(clientMsg, Registration.class);
            Registration player = new Registration();
            player = registrationData.registerPlayer(registrationData.getUsername(), registrationData.getPassword());

            if (player.getUsername() != null) {

                jsonSendBase.setStatus(1);
                jsonSendBase.setMessge("Registration Successfull");
                System.out.println(jsonSendBase.getType() + " " + jsonSendBase.getMessge() + " " + jsonSendBase.getStatus());
                jsonSend = JsonWrapper.toJson(jsonSendBase);
                mouth.println(jsonSend);

            } else {
                jsonSendBase.setStatus(0);
                jsonSendBase.setMessge("Registration Faild, The Username Exist before");
                jsonSend = JsonWrapper.toJson(jsonSendBase);
                mouth.println(jsonSend);
            }
        } else if (jsonRecieveBase.getType().equals(RequestTypes.OnlineGame.name())) {
            System.err.println("Online Game Recieved Message");
            OnlineGameModel onlineGameModel = JsonWrapper.fromJson(clientMsg, OnlineGameModel.class);
            String jsonData;
            if (onlineGameModel.getCurrentPlayerMark() == 'X') {
                onlineGameModel.setCurrentPlayerUserName(changeTurn(onlineGameModel));
                onlineGameModel.setCurrentPlayerMark('X');
                jsonData = JsonWrapper.toJson(onlineGameModel);
                System.err.println("Online Game " + jsonData);
                sendMessageToAll(jsonData);
            } else {
                onlineGameModel.setCurrentPlayerUserName(changeTurn(onlineGameModel));
                onlineGameModel.setCurrentPlayerMark('O');
                jsonData = JsonWrapper.toJson(onlineGameModel);
                System.err.println("Online Game " + jsonData);
                sendMessageToAll(jsonData);
            }
        } else if (jsonRecieveBase.getType().equals(RequestTypes.AvailPlayers.name())) {
            ListDB list = new ListDB();
            System.out.println("The Username of the Player That Login is received");   //for Test
            ArrayList<Player> playerList = list.getActivePlayersFromDatabase();
            //Remove this userNmae from the List
            //    playerList.removeIf(player -> player.getUserName().equals(clientMsg));   
            // Convert the playerList to JSON using JsonWrapper
            jsonSendBase.setStatus(1);
            jsonSendBase.setMessge("Send Available Players Successfull");
            jsonSendBase.setType(RequestTypes.AvailPlayers.name());
            System.out.println(jsonSendBase.getType() + " " + jsonSendBase.getMessge() + " " + jsonSendBase.getStatus());
            String jsonPlayers = JsonWrapper.toJson(playerList);
            mouth.println(jsonPlayers);
            System.out.println("Json Format For PlayerList: " + jsonPlayers);
            broadcastActivePlayers();
        } else if (jsonRecieveBase.getType().equals(RequestTypes.Invite.name())) {
            System.out.println("First Message from Aya" + clientMsg);
            OnlineBoard onlineBoard = JsonWrapper.fromJson(clientMsg, OnlineBoard.class);
            String jsonSend;
            onlineBoard.setType(RequestTypes.Invite.name());
            jsonSend = JsonWrapper.toJson(onlineBoard);
            System.out.println(" First Message to Aya" + jsonSend);
            sendMessageToAll(jsonSend);

        } else if (jsonRecieveBase.getType().equals(RequestTypes.InviteResponse.name())) {
            System.out.println("Second Message From Aya" + clientMessage);
            InviteResponseModel inviteResponseModel = JsonWrapper.fromJson(clientMsg, InviteResponseModel.class);
            handleInviteResponse(inviteResponseModel);
        } else if (jsonRecieveBase.getType().equals(RequestTypes.Logout.name())) {
            jsonSendBase.setType(RequestTypes.Logout.name());
            LogoutModel logoutModel = JsonWrapper.fromJson(clientMessage, LogoutModel.class);
            System.out.println("Loggedout Username: " + logoutModel);
            String loggedoutUserName = logoutModel.getUserName();

            DataBaseManager connection = new DataBaseManager();

            try {
                PreparedStatement updatePlayerStatus = connection.con.prepareStatement("UPDATE player SET status = 0 WHERE user_name = ?");
                updatePlayerStatus.setString(1, loggedoutUserName);
                updatePlayerStatus.executeUpdate();
                String jsonSend = JsonWrapper.toJson(jsonSendBase);
                sendMessageToAll(jsonSend);
            } catch (SQLException ex) {
                Logger.getLogger(PlayerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (jsonRecieveBase.getType().equals(RequestTypes.UpdateScore.name())) {
            WinnerModelResponse winnerModelResponse;
            winnerModelResponse = JsonWrapper.fromJson(clientMessage, WinnerModelResponse.class);
            ScoreUpdateHelper scoreUpdateHelper = new ScoreUpdateHelper(winnerModelResponse, dbManager);
            try {
                scoreUpdateHelper.updateUserScore();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (jsonRecieveBase.getType().equals(RequestTypes.Exiting.name())) {
            UserTerminatingResponse userTerminatingResponse;
            userTerminatingResponse = JsonWrapper.fromJson(clientMessage, UserTerminatingResponse.class);
            UserTerminatingSendModel userTerminatingSendModel = new UserTerminatingSendModel();
            userTerminatingSendModel.setMessge("The other user closed the game");
            userTerminatingSendModel.setStatus(1);
            userTerminatingSendModel.setSendModelFromRecieveModel(userTerminatingResponse);
            String jsonToSend;
            jsonToSend = JsonWrapper.toJson(userTerminatingSendModel);
            sendMessageToAll(jsonToSend);

        }
        
        
        else if (jsonRecieveBase.getType().equals(RequestTypes.Exit.name())) {
            
            ExitModel exitModel = JsonWrapper.fromJson(clientMessage, ExitModel.class);
            System.out.println("exited Username: " + exitModel);
            String loggedoutUserName = exitModel.getUserName();

            DataBaseManager connection = new DataBaseManager();

            try {
                PreparedStatement updatePlayerStatus = connection.con.prepareStatement("UPDATE player SET status = 0 WHERE user_name = ?");
                updatePlayerStatus.setString(1, loggedoutUserName);
                updatePlayerStatus.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private String changeTurn(OnlineGameModel onlineGameModel) {

        if (onlineGameModel.getCurrentPlayerUserName().equals(onlineGameModel.getPlayer1UserName())) {
            return onlineGameModel.getPlayer2UserName();
        } else {
            return onlineGameModel.getPlayer1UserName();
        }
    }

    public void sendInvitationToReceiverOnly(String receiverUserName, String message) {
        if (receiverUserName == null) {
            System.out.println("Invited Person is null");
            return;
        }
        for (PlayerHandler client : clientsVector) {

            if (client != null && client.getUserName() != null) {
                if (client.getUserName().equals(receiverUserName)) {
                    client.mouth.println(message);
                    break;
                }

            }
        }

    }

    public String getUserName() {

        if (clientMsg == null) {
            System.out.println("Client Message is Null");
            return null;
        }

        OnlineBoard onlineBoard = JsonWrapper.fromJson(clientMsg, OnlineBoard.class);
        if (onlineBoard != null) {
            return onlineBoard.getReceiverUserName();
        } else {
            System.out.println("OnlineBoard is null");
            return null;
        }

    }

    private void handleInviteResponse(InviteResponseModel inviteResponseModel) {

        boolean isAccepted = inviteResponseModel.getStatus() == 1;
        String invitedPlayer = inviteResponseModel.getReceiverUserName();

        InviteResponseModel invite = new InviteResponseModel();

        if (isAccepted) {
            System.out.println(invitedPlayer + " Accepted Your Invitation");

            invite.setType(RequestTypes.InviteResponse.name());
            invite.setMessge("accepted");
            invite.setStatus(1);
            invite.setSenderUserName(inviteResponseModel.getSenderUserName());
            invite.setReceiverUserName(inviteResponseModel.getReceiverUserName());

            String jsonSend = JsonWrapper.toJson(invite);
            sendMessageToAll(jsonSend);
        } else {
            System.out.println(invitedPlayer + "Rejected Your Invitation");
            invite.setType(RequestTypes.InviteResponse.name());
            invite.setMessge("rejected");
            invite.setStatus(0);
            invite.setSenderUserName(inviteResponseModel.getSenderUserName());
            invite.setReceiverUserName(inviteResponseModel.getReceiverUserName());

            String jsonSend = JsonWrapper.toJson(invite);
            sendMessageToAll(jsonSend);
        }

    }

}
