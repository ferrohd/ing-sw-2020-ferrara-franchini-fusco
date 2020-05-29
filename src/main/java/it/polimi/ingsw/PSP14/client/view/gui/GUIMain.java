package it.polimi.ingsw.PSP14.client.view.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class GUIMain extends Application {

    private static final BlockingQueue<Object> queue = new LinkedBlockingDeque<>();
    public static BlockingQueue<Object> getQueue() {
        return queue;
    }

    private static Stage initStage;
    public static Stage getStage() {
        return initStage;
    }

    /**
     * Entry point of JavaFX
     * @param primaryStage First Window created by JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        initStage = primaryStage;
        primaryStage.setResizable(false);
        initStage.setTitle("SANTORINI");
        initStage.show();
        queue.add(new Object());

        // Exit the program when pressing X button
        initStage.setOnCloseRequest(windowEvent -> {
            if (windowEvent.getEventType().equals(WindowEvent.WINDOW_CLOSE_REQUEST)) {
                System.exit(0);
            }
        });

        // Set app icon
        initStage.getIcons().add(new Image("/images/icon.png"));
    }

    static public void updateScene(Scene scene) {
        initStage.setScene(scene);
        initStage.show();
        initStage.setAlwaysOnTop(true);
        initStage.setAlwaysOnTop(false);
    }
}