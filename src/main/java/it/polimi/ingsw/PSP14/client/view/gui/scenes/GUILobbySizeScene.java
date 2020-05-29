package it.polimi.ingsw.PSP14.client.view.gui.scenes;

import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUILobbySizeScene implements Runnable {
        @Override
    public void run() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/scenes/LobbySize.fxml"));

            Scene scene = new Scene(root);

            GUIMain.updateScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
