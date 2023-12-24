package screen.server_screen;

import base.ServerBase;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ServerBaseScreen extends AnchorPane {

    protected final ImageView backgroundImage;
    protected final Label serverLabel;
    protected final Button startStopButton;
    private final ServerBase myServer;
    
    public ServerBaseScreen(Stage stage) {
        backgroundImage = new ImageView();
        serverLabel = new Label();
        startStopButton = new Button();
        myServer = new ServerBase();
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(323.0);
        setPrefWidth(600.0);

        backgroundImage.setFitHeight(407.0);
        backgroundImage.setFitWidth(600.0);
        backgroundImage.setPickOnBounds(true);
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setImage(new Image(getClass().getResource("/assets/cover.png").toExternalForm()));

        serverLabel.setLayoutX(219.0);
        serverLabel.setLayoutY(14.0);
        serverLabel.setText("Server");
        serverLabel.setTextFill(javafx.scene.paint.Color.valueOf("#fcd015"));
        serverLabel.setFont(new Font("Comic Sans MS Bold", 50.0));

        startStopButton.setLayoutX(242.0);
        startStopButton.setLayoutY(196.0);
        startStopButton.setMnemonicParsing(false);
        startStopButton.setPrefHeight(56.0);
        startStopButton.setPrefWidth(120.0);
        startStopButton.setText("Start");
        startStopButton.setTextFill(javafx.scene.paint.Color.valueOf("#fcd015"));
        startStopButton.setFont(new Font("Comic Sans MS Bold", 20.0));
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
        getChildren().add(backgroundImage);
        getChildren().add(serverLabel);
        getChildren().add(startStopButton);

    }
}
