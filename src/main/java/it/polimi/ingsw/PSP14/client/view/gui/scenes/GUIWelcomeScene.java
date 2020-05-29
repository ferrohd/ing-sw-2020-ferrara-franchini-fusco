package it.polimi.ingsw.PSP14.client.view.gui.scenes;

import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * Welcome screen loaded while connecting to the server. Shows the name's game along a nice background
 */
public class GUIWelcomeScene implements Runnable {
    @Override
    public void run() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/scenes/Welcome.fxml"));

            Scene scene = new Scene(root);

            GUIMain.updateScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
