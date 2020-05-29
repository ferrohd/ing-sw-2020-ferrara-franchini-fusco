package it.polimi.ingsw.PSP14.client.view.gui.scenes;

import it.polimi.ingsw.PSP14.client.view.cli.GodFactory;
import it.polimi.ingsw.PSP14.client.view.cli.UIGod;
import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;

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

            Scene scene = new Scene(root);

            GridPane godGrid = (GridPane) scene.lookup("#godgrid");
            godSplash = (ImageView) scene.lookup("#godsplash");
            godName = (Label) scene.lookup("#godname");
            godDescription = (Label) scene.lookup("#goddescription");
            godDescription.setWrapText(true);
            selectButton = (Button) scene.lookup("#selectButton");

            ((Label) scene.lookup("#title")).setText(title);


            String basepath = "file:src/main/resources/images/gods/";

            List<Node> icons = godGrid.getChildren();
            for(Node icon : icons) ((ImageView)icon).fitHeightProperty().bind(godGrid.heightProperty().divide(3.2));
            for(int i = 0; i < gods.size(); ++i) {
                ImageView img = (ImageView) icons.get(i);
                String godname = gods.get(i);
                String path = basepath + "icons/" + godname + ".png";
                img.setImage(new Image(path));
                img.setPreserveRatio(true);
                img.setOnMouseClicked((event) -> {
                    try {
                        UIGod god = GodFactory.getInstance().getGod(godname);
                        godName.setText(godname);
                        godDescription.setText(god.getDescription());
                        godSplash.setImage(new Image(basepath + godname + ".png"));
                    } catch(IOException e) {}
                });
            }

            selectButton.setOnMouseClicked((event) -> {
                if(!godName.getText().equals("")) {
                    selectButton.setDisable(true);
                    selectButton.setText("Waiting...");
                    try {
                        GUIMain.getQueue().put(gods.indexOf(godName.getText()));
                    } catch (InterruptedException e) {}
                }
            });

            GUIMain.updateScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
