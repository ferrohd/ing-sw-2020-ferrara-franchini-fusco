package it.polimi.ingsw.PSP14.client.controller;

import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class GUIUsernameSceneController {
    @FXML private TextField usernameField;
    @FXML private Button playBtn;

    @FXML protected void didClickPlay(ActionEvent event) throws InterruptedException {
        System.out.println("HELLO " + usernameField.getText());
        if (usernameField.getText().length() > 0) {
            playBtn.setDisable(true);
            GUIMain.getQueue().put(usernameField.getText());
        }
    }
}
