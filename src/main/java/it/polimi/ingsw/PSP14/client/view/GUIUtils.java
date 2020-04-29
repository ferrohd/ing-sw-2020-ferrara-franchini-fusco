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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GUIUtils extends Application {

    private static String username;
    private static String ip = null;
    private static final Text loginstatus = new Text();

    public static String getUsername() {
        return username;
    }

    public String getIp() {
        return ip;
    }

    public void setLoginstatus(String newStatus) {
        loginstatus.setText(newStatus);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("SANTORINI");
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
        hbBtn.getChildren().add(connectBtn);
        // Quit button
        Button quitButton = new Button("Quit");
        hbBtn.getChildren().add(quitButton);

        grid.add(hbBtn, 1, 4);

        // Status Text
        grid.add(loginstatus, 1, 6);

        stage.setScene(scene);
        stage.show();
    }
}