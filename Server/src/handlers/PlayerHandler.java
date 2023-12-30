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

import helpers.LoginDB;
import com.google.gson.Gson;
import database.DataBaseManager;
import helpers.RequestTypes;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.JsonReceiveBase;
import models.JsonSendBase;
import models.LoginResponseModel;
import models.LoginSendModel;
import models.Registration;
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

    private void handleOperation(String clientMessage) {
        jsonRecieveBase = JsonWrapper.fromJson(clientMsg, JsonReceiveBase.class);

        System.out.println("ss" + jsonRecieveBase.getType());

//        String[] parts = clientMsg.split(" ", 2); // hna bfok el msg L 2 parts "mode" + json
//        String mode = parts[0];
//        String jsonData = parts.length > 1 ? parts[1] : "";
//        System.out.println("mode" + mode);//bgrb bs eno faslhom
//        System.out.println("json" + jsonData);//bgrb bs eno faslhom
//
//        if ("Register".equals(mode)) {
//            // Handle registration
//            // gson 
//            Gson gson = new Gson();
//            Registration registrationData = gson.fromJson(jsonData, Registration.class);
//            //  System.out.println(registrationData);
//            Registration player = new Registration();
//            player = registrationData.registerPlayer(registrationData.getUsername(), registrationData.getPassword());
//
//            if (player.getUsername() != null) {
//                mouth.println("ok");
//            } else {
//                mouth.println("exist");
//            }
//
//        } else
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
                logineSendModel.setPlayerData(loginDB.getPlayerData(), jsonSendBase);
                jsonSend = JsonWrapper.toJson(logineSendModel);
                System.out.println(jsonSend);
                sendMessageToAll(jsonSend);
            } else {
                jsonSend = JsonWrapper.toJson(jsonSendBase);
                System.out.println(jsonSend);
                sendMessageToAll(jsonSend);
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
        } else if (jsonRecieveBase.getType().equals(RequestTypes.Move.toString())) {
            jsonSendBase.setType(RequestTypes.Move.toString());



        }


    }

}
