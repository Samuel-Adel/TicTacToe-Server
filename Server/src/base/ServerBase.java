/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import com.google.gson.Gson;
import handlers.PlayerHandler;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import models.Registration;
import server.Server;

/**
 *
 * @author Sasa Adel
 */
public class ServerBase {

    private Vector<Socket> clientsVector // what is the difference
            = new Vector<Socket>();
    public ServerSocket myServerSocket;
    protected volatile boolean isOn = false;
    Socket s;
    DataInputStream ear;
    Thread serverThread;
    PrintStream mouth;

    public ServerBase() {
        System.out.println("Server object created");
    }

    public void closeServer() throws IOException {
        if (isOn) {
            System.out.println("Server stopped");
            isOn = false;
            myServerSocket.close();
            serverThread.interrupt();
        }
    }

    public void startServer() throws IOException {
        if (!isOn) {
            System.out.println("Server started");
            myServerSocket = new ServerSocket(5005);
            serverThread = new Thread(() -> {
                while (isOn) {
                    try {
                        s = myServerSocket.accept();
                        ear = new DataInputStream(s.getInputStream());
                        mouth = new PrintStream(s.getOutputStream());
                        String clientMsg = ear.readLine();
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
                            Registration player=new Registration();
                            player = registrationData.registerPlayer(registrationData.getUsername(), registrationData.getPassword());
                            
                            if (player.getUsername() != null) {
                                mouth.println("ok");
                            } else {
                                mouth.println("exist");
                            }

                        }

                        clientsVector.add(s);
                        new PlayerHandler(s);
                    } catch (IOException ex) {
                        if (isOn) {
                            Logger.getLogger(ServerBase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            isOn = true;
            serverThread.start();
        }
    }

}
