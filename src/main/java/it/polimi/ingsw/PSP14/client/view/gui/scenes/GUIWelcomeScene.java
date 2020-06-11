package it.polimi.ingsw.PSP14.client.view.gui.scenes;

import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Welcome screen loaded while connecting to the server. Shows the name's game along a nice background
 */
public class GUIWelcomeScene implements Runnable {
    @Override
    public void run() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/scenes/Welcome.fxml"));
            GUIMain.getMainPane().setContent(root);

            GUIMain.updateScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
