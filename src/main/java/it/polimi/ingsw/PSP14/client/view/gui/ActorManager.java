package it.polimi.ingsw.PSP14.client.view.gui;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ActorManager {
    private final int WIDTH, HEIGHT;
    private final Map<String, Node> actors = new HashMap<>();
    private final Map<Node, Point3D> blocks = new HashMap<>();
    private final Node[][][] blockMatrix = new Node[5][5][4];

    private final double WALLS_SCALE = 1;
    private final double WALLS_X = 8.65;
    private final double WALLS_Y = 3.5;
    private final double WALLS_Z = 8.65;
    private final double BLOCK_X = 0.5;
    private final double BLOCK_Z = 0.5;
    private final double BLOCK_Y_1 = 1.2; // lv 1
    private final double BLOCK_Y_2 = 0; // lv2
    private final double BLOCK_Y_3 = -1.9; //lv 3
    private final double BLOCK_Y_4 = -3; // dome
    private final double BLOCK_SCALE = 0.35;
    private final double WORKER_HIDE_Y = 5;
    private final double WORKER_Y_1 = -1.1; // ground
    private final double WORKER_Y_2 = -2.3; // lv 1
    private final double WORKER_Y_3 = -3.4; // lv 2
    private final double WORKER_Y_4 = -4.25; // lv 3
    private final double WORKER_MOVE_SIZE = 2.5;

    public ActorManager(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        setupScenery();
        setupBuildings();
        setupWorkers();

        hideAllBlocks();

        // TESTING
        showBlock(3, 0, 3);
        showBlock(3, 1, 3);
        showBlock(3, 2, 3);
        showBlock(3, 3, 3); // dome
        showBlock(2, 0, 2);
        showBlock(2, 1, 2);
        showBlock(2, 2, 2); // lv 3
        showBlock(2, 0, 1);
        showBlock(2, 1, 1); // lv 2
        showBlock(1, 0, 1); // lv 1

        moveWorker(0, 0, 2, 3, 2);
        moveWorker(0, 1, 2, 2, 1);
        moveWorker(1, 0, 1, 1, 1);
        moveWorker(1, 1, 0, 0, 0);
    }

    /**
     * Add an actor to the scene
     * @param id the id of the actor
     * @param meshUrl an absolute path relative to the resource
     *                folder where the OBJ model resides.
     * @param textureUrl an absolute path relative to the resource
     *                   folder where the PNG texture resides.
     * @param position the position of the actor
     * @param scale the scale of the actor
     */
    public Node addActor(String id, String meshUrl, String textureUrl, Point3D position, double scale) {
        MeshView meshView = _initActor(meshUrl, position, scale);

        // Apply textures
        // This looks up to /resources/
        Image texture = new Image(textureUrl);
        PhongMaterial texturedMaterial = new PhongMaterial();
        texturedMaterial.setDiffuseMap(texture);
        meshView.setDrawMode(DrawMode.FILL);
        meshView.setMaterial(texturedMaterial);

        actors.put(id, meshView);
        return meshView;
    }

    /**
     * Add an actor to the scene
     * @param id the id of the actor
     * @param meshUrl an absolute path relative to the resource
     *                folder where the OBJ model resides.
     * @param color the color the mesh will have
     * @param position the position of the actor
     * @param scale the scale of the actor
     */
    public Node addActor(String id, String meshUrl, Color color, Point3D position, double scale) {
        MeshView meshView = _initActor(meshUrl, position, scale);

        // Apply textures
        PhongMaterial texturedMaterial = new PhongMaterial();
        texturedMaterial.setDiffuseColor(color);
        meshView.setDrawMode(DrawMode.FILL);
        meshView.setMaterial(texturedMaterial);

        actors.put(id, meshView);
        return meshView;
    }

    public Node getActorById(String id) {
        return actors.get(id);
    }

    public Point3D getBlockByNode(Node node) {
        return blocks.get(node);
    }

    /**
     * Init the scenery (sea, board, cliff, all the cosmetic stuff).
     */
    public void setupScenery() {
        addActor("sea", "/assets/Sea.obj", "/assets/Sea.png", new Point3D(0, 3, 0), 1);
        addActor("cliff", "/assets/Cliff.obj", "/assets/Cliff_v001.png", new Point3D(-0.1, 1.4, 0), 7.9);
        addActor("board", "/assets/Board.obj", "/assets/Cliff_v001.png", new Point3D(0, 0, 0), 1);
        addActor("outerWall", "/assets/OuterWall.obj", "/assets/Cliff_v001.png", new Point3D(WALLS_X, WALLS_Y, WALLS_Z), WALLS_SCALE);
        addActor("innerWall", "/assets/InnerWalls.obj", "/assets/Cliff_v001.png", new Point3D(WALLS_X, WALLS_Y, WALLS_Z), WALLS_SCALE);
    }

    /**
     * Init workers for all players. Unused workers are hidden from the board.
     * A worker ID is referenced as worker + playerNumber + workerNumber
     */
    public void setupWorkers() {
        addActor("worker00", "/assets/MaleBuilder.obj", "/assets/MaleBuilder_Blue_v001.png", new Point3D(0, WORKER_HIDE_Y, 0), 1);
        addActor("worker01", "/assets/FemaleBuilder.obj", "/assets/FemaleBuilder_Blue_v001.png", new Point3D(0, WORKER_HIDE_Y, 0), 1);
        addActor("worker10", "/assets/MaleBuilder.obj", "/assets/MaleBuilder_Orange_v001.png", new Point3D(0, WORKER_HIDE_Y, 0), 1);
        addActor("worker11", "/assets/FemaleBuilder.obj", "/assets/FemaleBuilder_Orange_v001.png", new Point3D(0, WORKER_HIDE_Y, 0), 1);
        addActor("worker20", "/assets/MaleBuilder.obj", "/assets/MaleBuilder_Pink_v001.png", new Point3D(0, WORKER_HIDE_Y, 0), 1);
        addActor("worker21", "/assets/FemaleBuilder.obj", "/assets/FemaleBuilder_Pink_v001.png", new Point3D(0, WORKER_HIDE_Y, 0), 1);
    }

    /**
     * Instantiates and add references for each possible building on the map:
     * this is a way to get better performances (assets pooling)
     */
    private void setupBuildings() {
        double BLOCK_SIZE = 2;

        for (int x = -2; x <= 2; x += 1) {
            for (int z = -2; z <= 2; z += 1) {
                final double newBlockX = BLOCK_X / 2 + BLOCK_X * x + x * BLOCK_SIZE;
                Node block1 = addActor(
                        "block1_" + (x+2) + "_" + (z+2),
                        "/assets/BuildingBlock01.obj",
                        "/assets/BuildingBlock01_v001.png",
                        new Point3D(newBlockX, BLOCK_Y_1, BLOCK_Z * z + z * BLOCK_SIZE),
                        BLOCK_SCALE);
                Node block2 = addActor(
                        "block2_" + (x+2) + "_" + (z+2),
                        "/assets/BuildingBlock02.obj",
                        "/assets/BuildingBlock02_v001.png",
                        new Point3D(newBlockX, BLOCK_Y_2, BLOCK_Z * z + z * BLOCK_SIZE),
                        BLOCK_SCALE);
                Node block3 = addActor(
                        "block3_" + (x+2) + "_" + (z+2),
                        "/assets/BuildingBlock03.obj",
                        "/assets/BuildingBlock03_v001.png",
                        new Point3D(newBlockX, BLOCK_Y_3, BLOCK_Z * z + z * BLOCK_SIZE),
                        BLOCK_SCALE);
                Node dome = addActor(
                        "dome_" + (x+2) + "_" + (z+2),
                        "/assets/Dome.obj",
                        Color.MIDNIGHTBLUE,
                        new Point3D(newBlockX, BLOCK_Y_4, BLOCK_Z * z + z * BLOCK_SIZE),
                        BLOCK_SCALE);
                blocks.put(block1, new Point3D(x+2, 0, z+2));
                blocks.put(block2, new Point3D(x+2, 1, z+2));
                blocks.put(block3, new Point3D(x+2, 2, z+2));
                blocks.put(dome, new Point3D(x+2, 3, z+2));
                blockMatrix[x+2][z+2][0] = block1;
                blockMatrix[x+2][z+2][1] = block2;
                blockMatrix[x+2][z+2][2] = block3;
                blockMatrix[x+2][z+2][3] = dome;
            }
        }
    }

    /**
     * Return a collection of nodes representing all the workers
     * in the game.
     * @return a collection of nodes
     */
    public Collection<Node> getAllWorkers() {
        List<String> workerKeys = actors.keySet().stream().filter(k -> k.startsWith("worker")).collect(Collectors.toList());
        List<Node> _ret = new ArrayList<>();
        for (String key : workerKeys) {
            Node _node = actors.get(key);
            if (_node != null)
                _ret.add(_node);
        }
        return _ret;
    }

    /**
     * Return a collection of nodes representing all the building components
     * in the game.
     * @return a collection of nodes
     */
    public Collection<Node> getAllBlocks() {
        return blocks.keySet();
    }

    /**
     * Hide all blocks; useful for setup phase.
     */
    private void hideAllBlocks() {
        for (Node block : getAllBlocks()) {
            block.setScaleX(0);
            block.setScaleY(0);
            block.setScaleZ(0);
        }
    }

    /**
     * Hide the block passed as a parameter with a cool animation.
     * @param block the block to hide
     */
    public void hideBlock(Node block) {
        if (!blocks.containsKey(block)) return;
        Timeline scaleBlockTimeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(block.scaleXProperty(), BLOCK_SCALE),
                        new KeyValue(block.scaleYProperty(), BLOCK_SCALE),
                        new KeyValue(block.scaleZProperty(), BLOCK_SCALE)
                ),
                new KeyFrame(
                        Duration.millis(500),
                        new KeyValue(block.scaleXProperty(), 0),
                        new KeyValue(block.scaleYProperty(), 0),
                        new KeyValue(block.scaleZProperty(), 0)
                )
        );
        scaleBlockTimeline.setCycleCount(1);
        scaleBlockTimeline.play();
    }

    /**
     * See {@link #hideBlock(Node)}
     * @param x position
     * @param y position
     * @param z position
     */
    public void hideBlock(int x, int y, int z) {
        hideBlock(blockMatrix[x][z][y]);
    }

    /**
     * Show the block passed as a parameter with a cool animation.
     * @param block the block to show
     */
    public void showBlock(Node block) {
        if (!blocks.containsKey(block)) return;
        int blockY = (int) blocks.get(block).getY();
        Timeline scaleBlockTimeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(block.translateYProperty(), _getBlockHeight(blockY) + 1),
                        new KeyValue(block.scaleXProperty(), 0),
                        new KeyValue(block.scaleYProperty(), 0),
                        new KeyValue(block.scaleZProperty(), 0)
                ),
                new KeyFrame(
                        Duration.millis(500),
                        new KeyValue(block.translateYProperty(), _getBlockHeight(blockY)),
                        new KeyValue(block.scaleXProperty(), BLOCK_SCALE),
                        new KeyValue(block.scaleYProperty(), BLOCK_SCALE),
                        new KeyValue(block.scaleZProperty(), BLOCK_SCALE)
                )
        );
        scaleBlockTimeline.setCycleCount(1);
        scaleBlockTimeline.play();
    }

    /**
     * See {@link #showBlock(Node)}
     * @param x position
     * @param y position
     * @param z position
     */
    public void showBlock(int x, int y, int z) {
        showBlock(blockMatrix[x][z][y]);
    }

    /**
     * Move a worker with a cool animation.
     * @param playerNumber the number of the worker's player (0, 1, 2)
     * @param workerNumber the number of the worker (0 or 1)
     * @param x target coordinate
     * @param y target coordinate
     * @param z target coordinate
     */
    public void moveWorker(int playerNumber, int workerNumber, int x, int y, int z) {
        Node worker = actors.get("worker" + playerNumber + "" + workerNumber);
        if (worker != null) {
            double _x = (x-2) * WORKER_MOVE_SIZE;
            double _y = _getWorkerHeight(y);
            double _z = (z-2) * WORKER_MOVE_SIZE;

            Timeline moveWorkerTimeline = new Timeline(
                    new KeyFrame(
                            Duration.seconds(0),
                            new KeyValue(worker.scaleXProperty(), worker.getTranslateX()),
                            new KeyValue(worker.scaleYProperty(), worker.getTranslateY()),
                            new KeyValue(worker.scaleZProperty(), worker.getTranslateZ())
                    ),
                    new KeyFrame(
                            Duration.millis(500),
                            new KeyValue(worker.translateXProperty(), _x),
                            new KeyValue(worker.translateYProperty(), _y),
                            new KeyValue(worker.translateZProperty(), _z)
                    )
            );
            moveWorkerTimeline.setCycleCount(1);
            moveWorkerTimeline.play();
        }
    }

//    public void highlightWorker(int playerNumber, int workerNumber) {
//
//    }
//
//    public void addToken(Node target, String color) {
//        MeshView token = (MeshView) addActor(
//                "token", "/assets/Token.obj",
//                "/assets/Token_v001.png",
//                new Point3D(target.getTranslateX(), target.getTranslateY() + 1, target.getTranslateZ()),
//                0.5);
//        token.blendModeProperty().setValue(BlendMode.MULTIPLY);
//
//        PhongMaterial mat = (PhongMaterial) token.getMaterial();
//        mat.setDiffuseColor(Color.valueOf(color));
//        token.setMaterial(mat);
//
//        // Spin it indefinitely
//        token.setRotationAxis(Rotate.Y_AXIS);
//        Timeline rotateAnimation = new Timeline(
//                new KeyFrame(
//                        Duration.seconds(0),
//                        new KeyValue(token.rotateProperty(), 0)
//                ),
//                new KeyFrame(
//                        Duration.seconds(8),
//                        new KeyValue(token.rotateProperty(), 360)
//                )
//        );
//        rotateAnimation.setCycleCount(Timeline.INDEFINITE);
//        rotateAnimation.play();
//    }

    private double _getBlockHeight(int y) {
        switch (y) {
            case 0:
                return BLOCK_Y_1;
            case 1:
                return BLOCK_Y_2;
            case 2:
                return BLOCK_Y_3;
            case 3:
                return BLOCK_Y_4;
            default:
                return 10;
        }
    }

    private double _getWorkerHeight(int y) {
        switch (y) {
            case 0:
                return WORKER_Y_1;
            case 1:
                return WORKER_Y_2;
            case 2:
                return WORKER_Y_3;
            case 3:
                return WORKER_Y_4;
            default:
                return 5;
        }
    }

    private MeshView _initActor(String meshUrl, Point3D position, double scale) {
        // Import 3D resource
        URL modelUrl = getClass().getResource(meshUrl);
        ObjModelImporter objImporter = new ObjModelImporter();

        objImporter.read(modelUrl);
        MeshView[] meshViews = objImporter.getImport();
        objImporter.close();

        // Get the first mesh (there should not be multiple meshes)
        MeshView meshView = meshViews[0];
        meshView.setCullFace(CullFace.BACK);

        // Set position
        meshView.setTranslateX(position.getX());
        meshView.setTranslateY(position.getY());
        meshView.setTranslateZ(position.getZ());

        // Set scale (unified)
        meshView.setScaleX(scale);
        meshView.setScaleY(scale);
        meshView.setScaleZ(scale);

        return meshView;
    }

}
