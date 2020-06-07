package it.polimi.ingsw.PSP14.client.view.gui.scenes;

import it.polimi.ingsw.PSP14.client.view.gui.GUIMain;
import it.polimi.ingsw.PSP14.client.view.gui.GameInfoPanelModel;
import it.polimi.ingsw.PSP14.client.view.gui.GameSceneModel;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.List;

import static com.sun.javafx.util.Utils.clamp;

public class GUIGameScene implements Runnable {

    private static final int VIEWPORT_X = 800;
    private static final int VIEWPORT_Y = 600;
    private final double CAMERA_ANGLE_MIN = -20;
    private final double CAMERA_ANGLE_MAX = -90;
    private final double MOUSE_SPEED = 0.2;
    private final double ZOOM_MIN = -20;
    private final double ZOOM_MAX = -60;
    private final double ZOOM_TIME = 200;

    private Rotate xRotate, yRotate = null;
    private Translate zoom = null;

    SimpleBooleanProperty isDragging = new SimpleBooleanProperty(false);
    SimpleDoubleProperty lastMouseX = new SimpleDoubleProperty(VIEWPORT_X * 0.5);
    SimpleDoubleProperty lastMouseY = new SimpleDoubleProperty(VIEWPORT_Y * 0.5);
    SimpleBooleanProperty isSelectingCell = new SimpleBooleanProperty(false);
    SimpleIntegerProperty playerId = new SimpleIntegerProperty(0);

    public void setIsDragging(boolean isDragging) {
        this.isDragging.set(isDragging);
    }

    public void setLastMouseX(double lastMouseX) {
        this.lastMouseX.set(lastMouseX);
    }

    public void setLastMouseY(double lastMouseY) {
        this.lastMouseY.set(lastMouseY);
    }

    public void setIsSelectingCell(boolean isSelectingCell) {
        this.isSelectingCell.set(isSelectingCell);
    }

    public void setPlayerId(int playerId) {
        this.playerId.set(playerId);
    }

    private GameSceneModel model;
    private GameInfoPanelModel infoPanelModel;

    @Override
    public void run() {
        model = new GameSceneModel();
        infoPanelModel = new GameInfoPanelModel();

        // Re-enable resize
        GUIMain.getStage().setResizable(true);

        // Create container for 3D scene and HUD
        BorderPane main = new BorderPane();
        //hBox.setAlignment(Pos.BOTTOM_LEFT);

        // Create a scene object
        SubScene scene = new SubScene(model.getRoot(), VIEWPORT_X, VIEWPORT_Y, true, SceneAntialiasing.BALANCED);
        Pane sceneContainer = new Pane(scene);
        scene.widthProperty().bind(sceneContainer.widthProperty());
        scene.heightProperty().bind(sceneContainer.heightProperty());

        // Create an info panel (infoPanel)
        VBox infoPanel = infoPanelModel.getRoot();

        main.setLeft(infoPanel);
        main.setCenter(sceneContainer);

        scene.heightProperty().bind(sceneContainer.heightProperty());
        scene.widthProperty().bind(sceneContainer.widthProperty());

        GUIMain.getMainPane().setContent(main);
        GUIMain.getStage().setHeight(VIEWPORT_Y);
        GUIMain.getStage().setWidth(VIEWPORT_X + infoPanel.getWidth());

        // Set background
        scene.setFill(Paint.valueOf("#21c8de"));

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
                } else {
                    // If I'm dragging
                    double deltaMouseX = (ev.getSceneX() - lastMouseX.get()) * MOUSE_SPEED;
                    double deltaMouseY = (ev.getSceneY() - lastMouseY.get()) * MOUSE_SPEED;

                    double newXAngle= clamp(CAMERA_ANGLE_MAX,xRotate.getAngle() - deltaMouseY, CAMERA_ANGLE_MIN);
                    double newYAngle= yRotate.getAngle() + deltaMouseX;
//                    getRotationTimeline(xRotate, xRotate.getAngle(), newXAngle, 20).play();
//                    getRotationTimeline(yRotate, yRotate.getAngle(), newYAngle, 20).play();
                    xRotate.setAngle(newXAngle);
                    yRotate.setAngle(newYAngle);
                }
                setLastMouseX(ev.getSceneX());
                setLastMouseY(ev.getSceneY());
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
        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            PickResult res = event.getPickResult();
            Node node = res.getIntersectedNode();
            // Stop doing stuff if I clicked on nothing
            if (node == null) return;

            // If I should handle picking cells
            if (isSelectingCell.get()) {
                if (node.getId().startsWith("sel")) {
                    int selectableId = Integer.parseInt(node.getId().substring(3));
                    System.out.println(selectableId);
                    GUIMain.getQueue().add(selectableId);
                } else if (node.getId().startsWith("worker")) {
                    List<Node> selectables = model.getAllSelectables();
                    Point workerPos = GameSceneModel.getBoardCoordinates(node);
                    for (int i = 0; i < selectables.size(); ++i) {
                        if (GameSceneModel.getBoardCoordinates(selectables.get(i)).equals(workerPos)) {
                            GUIMain.getQueue().add(i);
                            return;
                        }
                    }
                }
            }
        });

        GUIMain.updateScene();
    }

    public GameSceneModel getModel() { return model; }

    public GameInfoPanelModel getInfoPanelModel() {
        return infoPanelModel;
    }
}