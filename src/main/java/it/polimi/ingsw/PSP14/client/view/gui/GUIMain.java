package it.polimi.ingsw.PSP14.client.view.gui;

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
import javafx.stage.Stage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public class GUIMain extends Application {

    private static final BlockingQueue<Object> queue = new LinkedBlockingDeque<>();

    static String req;

    private static Stage initStage;

    public static Stage getStage() {
        return initStage;
    }

    public static BlockingQueue<Object> getQueue() {
        return queue;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initStage = primaryStage;
        initStage.setTitle("SANTORINI");
        initStage.show();
        queue.add(new Object());
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