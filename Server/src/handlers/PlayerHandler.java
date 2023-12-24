/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

/**
 *
 * @author Sasa Adel
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import base.LoginDB;
import com.google.gson.Gson;
import database.DataBaseManager;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.JsonBase;
import models.LoginModel;
import models.Registration;
//import org.json.JSONObject;

/**
 *
 * @author Sasa Adel
 */
public class PlayerHandler extends Thread {

    static private DataBaseManager dbManager;
    static int numberOfClients = 0;
    static int tryReset = 0;
    DataInputStream ear;
    PrintStream mouth;
    private Socket sc;
    String clientMsg;
    String str;
    static Vector<PlayerHandler> clientsVector // what is the difference
            = new Vector<PlayerHandler>();

    public PlayerHandler(Socket cs) {
        dbManager = new DataBaseManager();
        numberOfClients++;
        try {
            sc = cs;
            ear = new DataInputStream(cs.getInputStream());
            mouth = new PrintStream(cs.getOutputStream());
            mouth.println("Data Recieved CHat handler");
            PlayerHandler.clientsVector.add(this);
            start();
            clientMsg = ear.readLine();
            JsonBase jsonBase = JsonWrapper.fromJson(clientMsg, JsonBase.class);

            String[] parts = clientMsg.split(" ", 2); // hna bfok el msg L 2 parts "mode" + json
            String mode = parts[0];
            String jsonData = parts.length > 1 ? parts[1] : "";
            System.out.println("mode" + mode);//bgrb bs eno faslhom
            System.out.println("json" + jsonData);//bgrb bs eno faslhom

            if ("Register".equals(mode)) {
                // Handle registration
                // gson 
                Gson gson = new Gson();
                Registration registrationData = gson.fromJson(jsonData, Registration.class);
                //  System.out.println(registrationData);
                Registration player = new Registration();
                player = registrationData.registerPlayer(registrationData.getUsername(), registrationData.getPassword());

                if (player.getUsername() != null) {
                    mouth.println("ok");
                } else {
                    mouth.println("exist");
                }

            } else if (jsonBase.getMode().equals("Login")) {
                LoginModel loginModel = JsonWrapper.fromJson(clientMsg, LoginModel.class);
                LoginDB loginDB = new LoginDB(loginModel, dbManager);
                String message = loginDB.userLogin();
                boolean result = loginDB.checkLoginOperation();
            }
        } catch (IOException ex) {
            Logger.getLogger(PlayerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        try {

            while (ear != null && sc.isClosed() == false && (str = ear.readLine()) != null) {
                if (str.equals("Exit")) {
                    numberOfClients--;

                    continue;
                }
                sendMessageToAll(str);
            }
        } catch (SocketException ex) {
            System.out.println(ex.getMessage() + "\n");
            try {
                sc.close();
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
}
