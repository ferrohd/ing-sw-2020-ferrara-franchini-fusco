package it.polimi.ingsw.PSP14.client.view.gui.scenes;

import it.polimi.ingsw.PSP14.client.view.cli.GodFactory;
import it.polimi.ingsw.PSP14.client.view.cli.UIGod;
import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;

/**
 * A view that allows the players to choose the god they'll use in a match.
 */
public class GUIGodSelectScene implements Runnable {
    private final List<String> gods;
    private ImageView godSplash;
    private Label godName;
    private Label godDescription;
    private final String title;
    private Button selectButton;

    public GUIGodSelectScene(String title, List<String> gods) {
        this.gods = gods;
        this.title = title;
    }


    @Override
    public void run() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/scenes/GodSelect.fxml"));

            GUIMain.getMainPane().setContent(root);

            GridPane godGrid = (GridPane) root.lookup("#godgrid");
            godSplash = (ImageView) root.lookup("#godsplash");
            godName = (Label) root.lookup("#godname");
            godDescription = (Label) root.lookup("#goddescription");
            godDescription.setWrapText(true);
            selectButton = (Button) root.lookup("#selectButton");

            ((Label) root.lookup("#title")).setText(title);


            String basePath = "images/gods/";

            List<Node> icons = godGrid.getChildren();
            for (Node icon : icons) ((ImageView) icon).fitHeightProperty().bind(godGrid.heightProperty().divide(3.2));
            for (int i = 0; i < gods.size(); ++i) {
                ImageView img = (ImageView) icons.get(i);
                String godName = gods.get(i);
                String path = basePath + "icons/" + godName + ".png";
                img.setImage(new Image(getClass().getClassLoader().getResourceAsStream(path)));
                img.setPreserveRatio(true);
                img.setOnMouseClicked((event) -> {
                    try {
                        UIGod god = GodFactory.getInstance().getGod(godName);
                        this.godName.setText(godName);
                        godDescription.setText(god.getDescription());
                        godSplash.setImage(new Image(basePath + godName + ".png"));
                    } catch (IOException e) {
                    }
                });
            }

            selectButton.setOnMouseClicked((event) -> {
                if (!godName.getText().equals("")) {
                    selectButton.setDisable(true);
                    selectButton.setText("Waiting...");
                    try {
                        GUIMain.getQueue().put(gods.indexOf(godName.getText()));
                    } catch (InterruptedException e) {
                    }
                }
            });

            GUIMain.updateScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
