package it.polimi.ingsw.PSP14.client.view.gui.scenes;

import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class GUIGodSelectScene implements Runnable {
    private List<String> gods;

    public GUIGodSelectScene(List<String> gods) {
        this.gods = gods;
    }


    @Override
    public void run() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/scenes/GodSelect.fxml"));

            Scene scene = new Scene(root);

            GridPane godgrid = (GridPane) scene.lookup("#godgrid");
            List<Node> icons = godgrid.getChildren();
            for(int i = 0; i < gods.size(); ++i) {
                ImageView img = (ImageView) icons.get(i);
                img.setImage(new Image("/C:/Users/luigi/IdeaProjects/ing-sw-2020-ferrara-franchini-fusco/src/main/resources/images/gods/icons/" + gods.get(i) + ".png"));
            }

            GUIMain.getStage().setScene(scene);
            GUIMain.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
