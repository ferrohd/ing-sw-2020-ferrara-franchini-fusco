package it.polimi.ingsw.PSP14.client.view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This class handles the info panel shown at the side of the screen
 * during a game. It contains methods to register, update and unregister
 * player, gods, etc.
 */
public class GameInfoPanelModel {

    private static final double MAX_WIDTH = 140;
    private static final double H_PADDING = 5;
    private static final double V_PADDING = 5;

    private VBox hud;

    public GameInfoPanelModel() {
        init();

        // test
        registerPlayerInfo();
    }

    public Node getRoot() {
        return hud;
    }

    /**
     * Initialize the hud
     */
    private void init() {
        VBox hud = new VBox();
        hud.setMaxHeight(Integer.MAX_VALUE);
        hud.setMaxWidth(MAX_WIDTH);
        Background bg = new Background(new BackgroundImage(
                new Image("/images/panel_mid.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
        ));
        hud.setBackground(bg);
        hud.setAlignment(Pos.CENTER);
        hud.setPadding(new Insets(50, H_PADDING, 10, H_PADDING));
        hud.setMouseTransparent(true);
        this.hud = hud;
    }

    public void registerPlayerInfo() {
        VBox elem = new VBox();
        elem.setMaxHeight(150);
        elem.setPrefWidth(MAX_WIDTH - H_PADDING * 2);
        elem.setAlignment(Pos.CENTER);
        Background bg = new Background(
                new BackgroundFill(
                        Color.valueOf("00000022"),
                        new CornerRadii(5),
                        new Insets(0)));
        elem.setBackground(bg);
        elem.setPadding(new Insets(V_PADDING, 0, V_PADDING, 0));
        elem.setSpacing(V_PADDING);

        Text playerName = new Text();
        playerName.setFont(new Font(18));

        Text godName = new Text();
        godName.setFont(new Font(14));
        godName.setUnderline(true);

        ImageView godImage = new ImageView();
        godImage.setFitWidth(MAX_WIDTH / 2);
        godImage.setSmooth(true);
        godImage.setPreserveRatio(true);

        Text godDescription = new Text();
        godDescription.setWrappingWidth(MAX_WIDTH - H_PADDING * 4);

        elem.getChildren().addAll(playerName, godName, godImage, godDescription);


        // Test
        playerName.setText("Ada");
        godName.setText("Apollo");
        godImage.setImage(new Image("/images/gods/icons/Apollo.png"));
        godDescription.setText("Swappa due player.");

        hud.getChildren().add(elem);
    }
}
