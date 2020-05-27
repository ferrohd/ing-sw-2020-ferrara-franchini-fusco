package it.polimi.ingsw.PSP14.client.view.gui.scenes;

import it.polimi.ingsw.PSP14.client.view.cli.GodFactory;
import it.polimi.ingsw.PSP14.client.view.cli.UIGod;
import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

public class GUIGodSelectScene implements Runnable {
    private List<String> gods;
    private ImageView godSplash;
    private Label godName;
    private Label godDescription;
    private GridPane godGrid;
    private String title;

    public GUIGodSelectScene(String title, List<String> gods) {
        this.gods = gods;
        this.title = title;
    }


    @Override
    public void run() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/scenes/GodSelect.fxml"));

            Scene scene = new Scene(root);

            godGrid = (GridPane) scene.lookup("#godgrid");
            godSplash = (ImageView) scene.lookup("#godsplash");
            godName = (Label) scene.lookup("#godname");
            godDescription = (Label) scene.lookup("#goddescription");
            godDescription.setWrapText(true);

            ((Label) scene.lookup("#title")).setText(title);


            String basepath = "file:src/main/resources/images/gods/";

            List<Node> icons = godGrid.getChildren();
            for(int i = 0; i < gods.size(); ++i) {
                ImageView img = (ImageView) icons.get(i);
                String godname = gods.get(i);
                String path = basepath + "icons/" + godname + ".png";
                img.setImage(new Image(path));
                img.fitHeightProperty().bind(godGrid.heightProperty().divide(3.5));
                img.setPreserveRatio(true);
                img.setOnMouseClicked((event) -> {
                    try {
                        UIGod god = GodFactory.getInstance().getGod(godname);
                        godName.setText(god.getName());
                        godDescription.setText(god.getDescription());
                        godSplash.setImage(new Image(basepath + godname + ".png"));
                    } catch(IOException e) {}
                });
            }

            GUIMain.getStage().setScene(scene);
            GUIMain.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
