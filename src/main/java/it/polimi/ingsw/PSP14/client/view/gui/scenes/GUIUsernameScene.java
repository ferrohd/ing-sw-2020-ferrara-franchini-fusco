package it.polimi.ingsw.PSP14.client.view.gui.scenes;

import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * This scene asks the user to input the username to  be used in the game.
 */
public class GUIUsernameScene implements Runnable {
    @Override
    public void run() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/scenes/Username.fxml"));

            GUIMain.getMainPane().setContent(root);

            GUIMain.updateScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
