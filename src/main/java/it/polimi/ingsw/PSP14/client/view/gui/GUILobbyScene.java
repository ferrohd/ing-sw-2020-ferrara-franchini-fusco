package it.polimi.ingsw.PSP14.client.view.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class GUILobbyScene implements Runnable {
    @Override
    public void run() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene LobbyScene = new Scene(grid, 600, 500);

        // Lobby Size field
        Label lobbyLabel = new Label("Lobby Size:");
        grid.add(lobbyLabel,0,1);
        TextField lobbyField = new TextField();
        grid.add(lobbyField, 1, 1);
        //OK BUTTON
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        Button okButton = new Button("Ok");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GUIMain.getQueue().add(Integer.parseInt(lobbyField.getText()));
            }
        });
        hbBtn.getChildren().add(okButton);
        grid.add(hbBtn, 1, 4);

        GUIMain.getStage().setScene(LobbyScene);
    }
}
