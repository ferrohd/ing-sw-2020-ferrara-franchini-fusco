package it.polimi.ingsw.PSP14.client.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import it.polimi.ingsw.PSP14.client.view.GUIQueues;


public class GUIUtils extends Application {

    static final BlockingQueue<Object> reqQueue = GUIQueues.getReq();
    static final BlockingQueue<Object> resQueue = GUIQueues.getRes();

    static String req;

    private static String username;
    private static String ip = null;
    private static final Text loginStatus = new Text();
    private static Stage initStage;
    private static Integer lobbySize;

    public static String getUsername() {
        return username;
    }

    public static String getIp() {
        return ip;
    }

    public static Integer getLobbySize() {
        return  lobbySize;
    }

    public static void setLoginStatus(String newStatus) {
        loginStatus.setText(newStatus);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initStage = primaryStage;
        initStage();
        while(true) {
            req = (String) reqQueue.take();
            if (req.equals("welcome")) {
                welcome();
            }
            if (req.equals("exit")) {
                return;
            }
        }
    }

    public void initStage() {

        initStage.setTitle("SANTORINI");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 600, 500);

        // Welcome Text
        Text welcomeText = new Text("Welcome to Santorini!");
        welcomeText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(welcomeText, 0,0, 2, 1);

        // Username
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel,0,1);
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 1);

        // Server IP/URL
        Label ipLabel = new Label("IP:");
        grid.add(ipLabel,0,2);
        TextField ipField = new TextField();
        grid.add(ipField, 1, 2);

        //---- BUTTONS----
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        // Connect Button
        Button connectBtn = new Button("Connect");
        connectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ip = ipField.getText();
                username = usernameField.getText();
            }
        });
        hbBtn.getChildren().add(connectBtn);
        // Quit button
        Button quitButton = new Button("Quit");
        hbBtn.getChildren().add(quitButton);

        grid.add(hbBtn, 1, 4);

        // Status Text
        grid.add(loginStatus, 1, 6);

        initStage.setScene(scene);
        initStage.show();
    }

    public void welcome() throws Exception {

        Stage newWindow = new Stage();
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(initStage);

        newWindow.setTitle("SANTORINI");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 600, 500);

        // Welcome Text
        Text welcomeText = new Text("Welcome to Santorini!");
        welcomeText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(welcomeText, 0,0, 2, 1);

        // Username
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel,0,1);
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 1);

        // Server IP/URL
        Label ipLabel = new Label("IP:");
        grid.add(ipLabel,0,2);
        TextField ipField = new TextField();
        grid.add(ipField, 1, 2);

        //---- BUTTONS----
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        // Connect Button
        Button connectBtn = new Button("Connect");
        connectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ip = ipField.getText();
                username = usernameField.getText();
            }
        });
        hbBtn.getChildren().add(connectBtn);
        // Quit button
        Button quitButton = new Button("Quit");
        hbBtn.getChildren().add(quitButton);

        grid.add(hbBtn, 1, 4);

        // Status Text
        grid.add(loginStatus, 1, 6);

        newWindow.setScene(scene);
        newWindow.show();
    }

    public static void askLobbySize() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene LobbyScene = new Scene(grid, 600, 500);

        // Lobby Size field
        Label lobbyLabel = new Label("Lobby Size:");
        grid.add(lobbyLabel,0,1);
        TextField lobbyField = new TextField();
        grid.add(lobbyField, 1, 1);
        //OK BUTTON
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        Button okButton = new Button("Ok");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resQueue.add(Integer.parseInt(lobbyField.getText()));
            }
        });
        hbBtn.getChildren().add(okButton);
        grid.add(hbBtn, 1, 4);

        initStage.setScene(LobbyScene);
    }
    public static void notifyAlert(String notifyMessage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene LobbyScene = new Scene(grid, 600, 500);

        Text welcomeText = new Text(notifyMessage);
        welcomeText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        grid.add(welcomeText, 0,0, 2, 1);
    }
}