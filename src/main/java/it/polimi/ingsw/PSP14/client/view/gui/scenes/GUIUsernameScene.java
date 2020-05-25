package it.polimi.ingsw.PSP14.client.view.gui.scenes;

import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class GUIUsernameScene implements Runnable {
    @Override
    public void run() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/scenes/Username.fxml"));

            Scene scene = new Scene(root);

            GUIMain.getStage().setScene(scene);
            GUIMain.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
