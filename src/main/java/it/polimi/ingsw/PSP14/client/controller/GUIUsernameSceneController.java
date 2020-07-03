package it.polimi.ingsw.PSP14.client.controller;

import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * The controller of {@link it.polimi.ingsw.PSP14.client.view.gui.scenes.GUIUsernameScene} FXML view.
 */
public class GUIUsernameSceneController {
    @FXML
    private TextField usernameField;
    @FXML
    private Button playBtn;

    @FXML
    protected void didClickPlay(ActionEvent event) throws InterruptedException {
        if (usernameField.getText().length() > 0) {
            playBtn.setDisable(true);
            usernameField.setDisable(true);
            GUIMain.getQueue().put(usernameField.getText());
        }
    }
}
