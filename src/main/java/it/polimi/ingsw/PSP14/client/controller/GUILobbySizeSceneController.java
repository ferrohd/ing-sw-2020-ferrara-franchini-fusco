package it.polimi.ingsw.PSP14.client.controller;

import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * The controller of {@link it.polimi.ingsw.PSP14.client.view.gui.scenes.GUILobbySizeScene} FXML view.
 */
public class GUILobbySizeSceneController {
    @FXML private Button btn2;
    @FXML private Button btn3;

    @FXML protected void didClickTwoPlayers(ActionEvent event) throws InterruptedException {
        System.out.println("Set lobby with size 2");
        disableButtons();
        GUIMain.getQueue().put(2);
    }

    @FXML protected void didClickThreePlayers(ActionEvent event) throws InterruptedException {
        System.out.println("Set lobby with size 3");
        disableButtons();
        GUIMain.getQueue().put(3);
    }

    private void disableButtons() {
        btn2.setDisable(true);
        btn3.setDisable(true);
    }
}
