package it.polimi.ingsw.PSP14.client.view.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.Random;

import static com.sun.javafx.util.Utils.clamp;

public class GUIGameScene implements Runnable {

    private static final int VIEWPORT_X = 800;
    private static final int VIEWPORT_Y = 600;
    private final double CAMERA_ANGLE_MIN = -20;
    private final double CAMERA_ANGLE_MAX = -90;
    private final double MOUSE_SPEED = 0.75;
    private final double ZOOM_MIN = -20;
    private final double ZOOM_MAX = -60;
    private final double ZOOM_TIME = 200;

    private Scene scene = null;
    private Rotate xRotate, yRotate = null;
    private Translate zoom = null;

    SimpleBooleanProperty isDragging = new SimpleBooleanProperty(false);
    SimpleDoubleProperty startMouseX = new SimpleDoubleProperty(VIEWPORT_X * 0.5);
    SimpleDoubleProperty startMouseY = new SimpleDoubleProperty(VIEWPORT_Y * 0.5);

    public void setIsDragging(boolean isDragging) {
        this.isDragging.set(isDragging);
    }

    public void setStartMouseX(double startMouseX) {
        this.startMouseX.set(startMouseX);
    }

    public void setStartMouseY(double startMouseY) {
        this.startMouseY.set(startMouseY);
    }

    @Override
    public void run() {

        ActorManager actors = new ActorManager(VIEWPORT_X, VIEWPORT_Y);

        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(Color.LIGHTYELLOW);
        Group root = new Group(
                actors.getActorById("sea"),
                actors.getActorById("board"),
                actors.getActorById("cliff"),
                actors.getActorById("outerWall"),
                actors.getActorById("innerWall"));
        root.getChildren().addAll(actors.getAllWorkers());
        root.getChildren().addAll(actors.getAllBlocks());
        root.getChildren().add(ambientLight);

        // Create a scene object
        scene = new Scene(root, VIEWPORT_X, VIEWPORT_Y, true);
        scene.setFill(Paint.valueOf("#f4d7ad"));

        // Create a camera with its transforms.
        Translate pivot = new Translate(0, 0, 0);
        xRotate = new Rotate(-30, Rotate.X_AXIS);
        yRotate = new Rotate(0, Rotate.Y_AXIS);
        zoom = new Translate(0, 0, -50);
        Camera pCamera = new PerspectiveCamera(true);

        // We change the pivot of the camera object
        pCamera.getTransforms().addAll (
                pivot,
                yRotate,
                xRotate,
                zoom
        );
        scene.setCamera(pCamera);

        // Handle Camera
        scene.addEventHandler(MouseEvent.ANY, ev -> {
            if (ev.getSceneX() < 0 || ev.getSceneX() > scene.getWidth() || ev.getSceneY() < 0 || ev.getSceneY() > scene.getHeight())
                return;
            // Start dragging the camera if mouse button is down
            if (ev.isSecondaryButtonDown() || ev.isPrimaryButtonDown()) {
                // If I'm not yet dragging
                if (!isDragging.get()) {
                    setIsDragging(true);
                    setStartMouseX(ev.getSceneX());
                    setStartMouseY(ev.getSceneY());
                } else {
                    // If I'm dragging
                    double deltaMouseX = (ev.getSceneX() - startMouseX.get()) / VIEWPORT_X * MOUSE_SPEED;
                    double deltaMouseY = (ev.getSceneY() - startMouseY.get()) / VIEWPORT_Y * MOUSE_SPEED;

                    double newXAngle= clamp(CAMERA_ANGLE_MAX,xRotate.getAngle() - deltaMouseY, CAMERA_ANGLE_MIN);
                    double newYAngle= yRotate.getAngle() + deltaMouseX;
//                    getRotationTimeline(xRotate, xRotate.getAngle(), newXAngle, 20).play();
//                    getRotationTimeline(yRotate, yRotate.getAngle(), newYAngle, 20).play();
                    xRotate.setAngle(newXAngle);
                    yRotate.setAngle(newYAngle);
                }
            } else {
                setIsDragging(false);
            }
        });

        // Handle Zoom
        scene.addEventHandler(ScrollEvent.SCROLL, scrollEvent -> {
            // Zoom in
            double newZoom = clamp(ZOOM_MAX,zoom.getZ() + 10 * Math.signum(scrollEvent.getDeltaY()),  ZOOM_MIN);
            Timeline zoomTimeline = new Timeline(
                    new KeyFrame(
                            Duration.seconds(0),
                            new KeyValue(zoom.zProperty(), zoom.getZ())
                    ),
                    new KeyFrame(
                            Duration.millis(ZOOM_TIME),
                            new KeyValue(zoom.zProperty(), newZoom)
                    )
            );
            zoomTimeline.setCycleCount(1);
            zoomTimeline.play();
        });

        // Handle Selection
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            PickResult res = event.getPickResult();
            Node node = res.getIntersectedNode();
            if (node != null) {
                // Do stuff
                actors.moveWorker(0, 0, new Random().nextInt(4), 0, new Random().nextInt(4));
            }
        });

        GUIMain.getStage().setScene(scene);
        GUIMain.getStage().show();
    }
}