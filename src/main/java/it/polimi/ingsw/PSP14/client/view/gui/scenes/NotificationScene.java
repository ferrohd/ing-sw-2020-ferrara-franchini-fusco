package it.polimi.ingsw.PSP14.client.view.gui.scenes;

import it.polimi.ingsw.PSP14.client.view.gui.GUI;
import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;

import javax.swing.*;

public class NotificationScene implements Runnable {
    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    final String textMessage;

    public NotificationScene(String textMessage) {
        this.textMessage = textMessage;
    }
    @Override
    public void run() {
        Scene scene = GUIMain.getStage().getScene();
        Parent pane = scene.getRoot();

        if (!(pane instanceof NotificationPane)) {
            NotificationPane toast = new NotificationPane(pane);
            toast.setText(this.textMessage);
            scene = new Scene(toast, scene.getWidth(), scene.getHeight());
            GUIMain.getStage().setScene(scene);
            toast.show();
        } else {
            ((NotificationPane) pane).setText(this.textMessage);
            ((NotificationPane) pane).show();
        }
    }
}