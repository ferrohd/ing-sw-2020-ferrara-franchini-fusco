package it.polimi.ingsw.PSP14.client.view.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class GUILobbySizeScene implements Runnable{

    @Override
    public void run() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/scenes/LobbySize.fxml"));

            Scene scene = new Scene(root);

            GUIMain.getStage().setScene(scene);
            GUIMain.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
