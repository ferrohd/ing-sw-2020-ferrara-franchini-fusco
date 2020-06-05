package it.polimi.ingsw.PSP14.client.view.gui;

import it.polimi.ingsw.PSP14.client.view.cli.GodFactory;
import it.polimi.ingsw.PSP14.client.view.cli.UIGod;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;

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
    }

    public VBox getRoot() {
        return hud;
    }

    /**
     * Initialize the hud
     */
    private void init() {
        VBox hud = new VBox();
        hud.setMaxHeight(Integer.MAX_VALUE);
        hud.setMaxWidth(200);
        hud.setMinWidth(200);
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
        hud.setSpacing(V_PADDING);

        // Disable mouse interaction
        hud.setMouseTransparent(true);

        StackPane container = new StackPane();
        container.setPrefHeight(150);
        container.setMaxWidth(MAX_WIDTH - H_PADDING * 2);


        DropShadow ds = new DropShadow();
        ds.setSpread(0.75f);
        ds.setColor(Color.valueOf("eeeeeebb"));

        Text phaseText = new Text();
        phaseText.setText("Loading...");
        phaseText.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        phaseText.setWrappingWidth(MAX_WIDTH - H_PADDING * 4);
        phaseText.setEffect(ds);
        phaseText.setCache(true);

        container.getChildren().add(phaseText);

        hud.getChildren().add(container);

        this.hud = hud;
    }

    public void registerPlayerInfo(String player, String god) {
        // Get god data
        UIGod _god;
        try {
            _god = GodFactory.getInstance().getGod(god);
        } catch (IOException e) {
            _god = new UIGod("???", "???", "???", "???");
            e.printStackTrace();
        }

        Image godImage = new Image("/images/gods/icons/" + god + ".png");

        ImageView godBackground = new ImageView();
        godBackground.setImage(godImage);
        godBackground.setFitWidth(MAX_WIDTH - H_PADDING * 2);
        godBackground.setPreserveRatio(true);
        godBackground.setOpacity(0.5);
        godBackground.setSmooth(true);

        // Create container
        StackPane container = new StackPane();
        container.setPrefHeight(150);
        container.setMaxWidth(MAX_WIDTH - H_PADDING * 2);

        // Create "text" container
        VBox box = new VBox();
        box.setMaxHeight(150);
        box.setPrefWidth(MAX_WIDTH - H_PADDING * 2);
        box.setAlignment(Pos.BOTTOM_CENTER);
        box.setSpacing(V_PADDING);

        // Add white shadow to improve legibility
        DropShadow ds = new DropShadow();
        ds.setSpread(0.75f);
        ds.setColor(Color.valueOf("eeeeeebb"));

        // Player name
        Text playerName = new Text();
        playerName.setText(player);
        playerName.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        playerName.setEffect(ds);
        playerName.setCache(true);

        // God name
        Text godName = new Text();
        godName.setText(_god.getName());
        godName.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        godName.setUnderline(true);
        godName.setEffect(ds);
        godName.setCache(true);

        // God description container
        VBox godText = new VBox();
        godText.setAlignment(Pos.TOP_LEFT);
        godText.setPadding(new Insets(0, H_PADDING, 0, H_PADDING));

        // God ability (the header/effect)
        Text godAbility = new Text();
        godAbility.setText(_god.getAbility() + ":");
        godAbility.setFont(Font.font("Georgia", FontWeight.BOLD, 12));
        godAbility.setEffect(ds);
        godAbility.setCache(true);

        // God effect description
        Text godDescription = new Text();
        godDescription.setText(_god.getDescription());
        godDescription.setFont(Font.font("Georgia", FontWeight.NORMAL, 12));
        godDescription.setWrappingWidth(MAX_WIDTH - H_PADDING * 4);
        godDescription.setEffect(ds);
        godDescription.setCache(true);

        godText.getChildren().addAll(godAbility, godDescription);

        box.getChildren().addAll(playerName, godName, godText);

        container.getChildren().addAll(godBackground, box);

        hud.getChildren().add(container);
    }
}
