package it.polimi.ingsw.PSP14.client.view.gui.scenes;

import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.controlsfx.control.NotificationPane;

import java.io.IOException;

/**
 * Welcome screen loaded while connecting to the server. Shows the name's game along a nice background
 */
public class GUIWelcomeScene implements Runnable {
    @Override
    public void run() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/scenes/Welcome.fxml"));

            NotificationPane mainPane = new NotificationPane(root);
            Scene mainScene = new Scene(mainPane);

            GUIMain.getStage().setScene(mainScene);

            GUIMain.updateScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
