package it.polimi.ingsw.PSP14.client.view.gui.scenes;

import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUIFirstPlayerScene implements Runnable {
    List<String> names;

    public GUIFirstPlayerScene(List<String> names) {
        this.names = names;
    }

    @Override
    public void run() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/scenes/FirstPlayer.fxml"));

            Scene scene = new Scene(root);

            List<Button> buttons = new ArrayList<>(3);
            for (int i = 0; i < names.size(); ++i) buttons.add((Button) scene.lookup("#button" + i));
            for (int i = 0; i < names.size(); ++i) {
                Integer index = i;
                buttons.get(i).setText(names.get(i));
                buttons.get(i).setOnMouseClicked((event) -> {
                    for(Button b : buttons) b.setDisable(true);
                    GUIMain.getQueue().add(index);
                });
            }

            if(names.size() == 2) {
                scene.lookup("#button2").setVisible(false);
                scene.lookup("#button2").setManaged(false);
            }

            GUIMain.updateScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
