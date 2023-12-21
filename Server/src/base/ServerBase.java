/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import handlers.PlayerHandler;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

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
