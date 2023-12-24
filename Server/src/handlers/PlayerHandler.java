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
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
/**
 *
 * @author Sasa Adel
 */
public class PlayerHandler extends Thread {

    static int numberOfClients = 0;
    static int tryReset = 0;
    DataInputStream ear;
    PrintStream mouth;
    private Socket sc;
    String str;
    static Vector<PlayerHandler> clientsVector // what is the difference
            = new Vector<PlayerHandler>();

    public PlayerHandler(Socket cs) {
        numberOfClients++;
        try {
            sc = cs;
            ear = new DataInputStream(cs.getInputStream());
            mouth = new PrintStream(cs.getOutputStream());
            mouth.println("Data Recieved CHat handler");
            PlayerHandler.clientsVector.add(this);
            start();
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
