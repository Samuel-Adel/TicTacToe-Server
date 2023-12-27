package screen.server_screen;

import base.ServerBase;
import database.DataBaseManager;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ServerBaseScreen extends AnchorPane {

    protected final Label label;
    protected final Button startStopButton;
    protected final Label label0;
    protected final Label label1;
    protected final Label lableOnnlinePlayers;
    protected final Label lableNumOfPlayers;
    private final ServerBase myServer;

    public ServerBaseScreen(Stage stage) {

        label = new Label();
        startStopButton = new Button();
        label0 = new Label();
        label1 = new Label();
        lableOnnlinePlayers = new Label();
        lableNumOfPlayers = new Label();
        myServer = new ServerBase();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(416.0);
        setPrefWidth(764.0);
        setStyle("-fx-background-color: #3C7CD7;");

        label.setLayoutX(301.0);
        label.setLayoutY(14.0);
        label.setText("Server");
        label.setTextFill(javafx.scene.paint.Color.valueOf("#fcd015"));
        label.setFont(new Font("Comic Sans MS Bold", 50.0));

        startStopButton.setLayoutX(322.0);
        startStopButton.setLayoutY(266.0);
        startStopButton.setMnemonicParsing(false);
        startStopButton.setPrefHeight(56.0);
        startStopButton.setPrefWidth(120.0);
        startStopButton.setText("Start");
        startStopButton.setTextFill(javafx.scene.paint.Color.valueOf("#fcd015"));
        startStopButton.setFont(new Font("Comic Sans MS Bold", 20.0));
        startStopButton.setStyle(
                "-fx-background-color: white; "
                + "-fx-effect: dropshadow(gaussian, black, 10, 0.5, 0, 0); "
                + "-fx-border-color: black; "
                + "-fx-border-width: 3px;"
                + "-fx-background-radius: 10; "
                + "-fx-border-radius: 10;"
        );
        stage.setOnCloseRequest((event) -> {
            try {
                myServer.closeServer();
            } catch (IOException ex) {
                Logger.getLogger(ServerBaseScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        label0.setLayoutX(50.0);
        label0.setLayoutY(99.0);
        label0.setText("Number of players");
        label0.setTextFill(javafx.scene.paint.Color.valueOf("#fcd015"));
        label0.setFont(new Font("Comic Sans MS Bold", 25.0));

        label1.setLayoutX(552.0);
        label1.setLayoutY(99.0);
        label1.setText("Online players");
        label1.setTextFill(javafx.scene.paint.Color.valueOf("#fcd015"));
        label1.setFont(new Font("Comic Sans MS Bold", 25.0));

        lableOnnlinePlayers.setLayoutX(637.0);
        lableOnnlinePlayers.setLayoutY(187.0);
        lableOnnlinePlayers.setText("0");
        lableOnnlinePlayers.setTextFill(javafx.scene.paint.Color.valueOf("#fcd015"));
        lableOnnlinePlayers.setFont(new Font("Comic Sans MS Bold", 25.0));

        lableNumOfPlayers.setLayoutX(96.0);
        lableNumOfPlayers.setLayoutY(180.0);
        lableNumOfPlayers.setText("0");
        lableNumOfPlayers.setTextFill(javafx.scene.paint.Color.valueOf("#fcd015"));
        lableNumOfPlayers.setFont(new Font("Comic Sans MS Bold", 25.0));

        getChildren().add(label);
        getChildren().add(startStopButton);
        getChildren().add(label0);
        getChildren().add(label1);
        getChildren().add(lableOnnlinePlayers);
        getChildren().add(lableNumOfPlayers);
        serverButton();
        getNumOfPlayers();
        getNumOfOnlinePlayers();

    }

    private void serverButton() {
        startStopButton.setOnAction((event) -> {
            if (startStopButton.getText().equals("Start")) {
                try {
                    myServer.startServer();
                } catch (IOException ex) {
                    Logger.getLogger(ServerBaseScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
                startStopButton.setText("Stop");
            } else {
                try {
                    myServer.closeServer();
                } catch (IOException ex) {
                    Logger.getLogger(ServerBaseScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
                startStopButton.setText("Start");
            }

        });

    }

    private void getNumOfPlayers() {
        DataBaseManager connection = new DataBaseManager();
        new Thread(() -> {
            try {
                PreparedStatement preparedStatementAllPlayers = connection.con.prepareStatement("SELECT COUNT(*) AS num_rows FROM player");

                ResultSet resultSet = preparedStatementAllPlayers.executeQuery();
                if (resultSet.next()) {
                    int numOfPlayers = resultSet.getInt("num_rows");

                    lableNumOfPlayers.setText(String.valueOf(numOfPlayers));
                }

                resultSet.close();
                preparedStatementAllPlayers.close();
            } catch (SQLException ex) {
                Logger.getLogger(ServerBaseScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();

    }

    private void getNumOfOnlinePlayers() {
        DataBaseManager connection = new DataBaseManager();
        new Thread(() -> {

            try {
                PreparedStatement preparedStatementAllPlayers = connection.con.prepareStatement("SELECT COUNT(*) AS num_rows FROM player where status = 1");

                ResultSet resultSet = preparedStatementAllPlayers.executeQuery();
                if (resultSet.next()) {
                    int numOfPlayers = resultSet.getInt("num_rows");

                    lableOnnlinePlayers.setText(String.valueOf(numOfPlayers));
                }

                resultSet.close();
                preparedStatementAllPlayers.close();
            } catch (SQLException ex) {
                Logger.getLogger(ServerBaseScreen.class.getName()).log(Level.SEVERE, null, ex);
            }

        }).start();

    }

}
