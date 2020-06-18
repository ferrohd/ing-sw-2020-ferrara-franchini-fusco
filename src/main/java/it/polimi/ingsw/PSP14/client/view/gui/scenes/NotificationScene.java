package it.polimi.ingsw.PSP14.client.view.gui.scenes;

import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.util.Duration;
import org.controlsfx.control.NotificationPane;

/**
 * Toast-like notification to notify the user of minor details.
 */
public class NotificationScene implements Runnable {

    final String textMessage;

    public NotificationScene(String textMessage) {
        this.textMessage = textMessage;
    }

    @Override
    public void run() {
        Scene scene = GUIMain.getStage().getScene();

        NotificationPane pane = (NotificationPane) scene.getRoot();
        pane.setText(this.textMessage);
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> pane.hide());
        delay.play();
        pane.show();
    }
}