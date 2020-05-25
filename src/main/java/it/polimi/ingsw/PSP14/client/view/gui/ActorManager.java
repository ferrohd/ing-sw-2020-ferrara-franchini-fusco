package it.polimi.ingsw.PSP14.client.view.gui;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import it.polimi.ingsw.PSP14.server.model.board.Point;
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
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ActorManager {

    private static final double WALLS_X = 8.65;
    private static final double WALLS_SCALE = 1;
    private static final double WALLS_Y = 3.5;
    private static final double WALLS_Z = 8.65;
    private static final double BLOCK_X = 0.5;
    private static final double BLOCK_Z = 0.5;
    private static final double BLOCK_Y_1 = 1.2; // lv 1
    private static final double BLOCK_Y_2 = 0; // lv2
    private static final double BLOCK_Y_3 = -1.9; //lv 3
    private static final double BLOCK_Y_4 = -3; // dome
    private static final double BLOCK_SCALE = 0.35;
    private static final double WORKER_HIDE_Y = 5;
    private static final double WORKER_Y_1 = -1; // ground
    private static final double WORKER_Y_2 = -2.3; // lv 1
    private static final double WORKER_Y_3 = -3.4; // lv 2
    private static final double WORKER_Y_4 = -4.25; // lv 3

    // Contains all actors, accessible with an ID
    private final Map<String, Node> actors = new HashMap<>();

    public ActorManager() {

        setupScenery();
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
        // Import 3D resource
        URL modelUrl = ActorFactory.class.getResource(meshUrl);
        ObjModelImporter objImporter = new ObjModelImporter();

        objImporter.read(modelUrl);
        MeshView[] meshViews = objImporter.getImport();
        objImporter.close();

        // Get the first mesh (there should not be multiple meshes)
        MeshView meshView = meshViews[0];
        meshView.setCullFace(CullFace.BACK);

        // Apply textures
        // This looks up to /resources/
        Image texture = new Image(textureUrl);
        PhongMaterial texturedMaterial = new PhongMaterial();
        texturedMaterial.setDiffuseMap(texture);
        meshView.setDrawMode(DrawMode.FILL);
        meshView.setMaterial(texturedMaterial);

        meshView.setTranslateX(position.getX());
        meshView.setTranslateY(position.getY());
        meshView.setTranslateZ(position.getZ());

        meshView.setScaleX(scale);
        meshView.setScaleY(scale);
        meshView.setScaleZ(scale);

        actors.put(id, meshView);
        return meshView;
    }

    public Node getActorById(String id) {
        return actors.get(id);
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

    public static String getWorkerActorId(int player, int workerId) {
        return "worker" + player + workerId;
    }

    public void addWorker(Point point, int workerId, int playerId) {
        Node worker = ActorFactory.getWorker(playerId, workerId);
        Point3D target = getSceneWorkerCoordinates(point);
        setNodeToPoint3D(worker, target);
        addToActorsAndRegister(getWorkerActorId(playerId, workerId), worker);
    }

    public Node getWorker(int player, int worker) {
        return actors.get(getWorkerActorId(player, worker));
    }

    /**
     * Return a collection of nodes representing all the workers
     * in the game.
     * @return a collection of nodes
     */
    public Collection<Node> getAllWorkers() {
        List<String> _keys = actors.keySet().stream().filter(k -> k.startsWith("worker")).collect(Collectors.toList());
        List<Node> _ret = new ArrayList<>();
        for (String key : _keys) {
            Node _node = actors.get(key);
            if (_node != null)
                _ret.add(_node);
        }
        return _ret;
    }

    /**
     * Return a list of nodes representing all the building components
     * in the game.
     * @return a collection of nodes
     */
    public List<Node> getAllBlocks() {
        List<String> _keys = actors.keySet().stream().filter(k -> k.startsWith("block")).collect(Collectors.toList());
        List<Node> _blocks = new ArrayList<>();
        for (String key : _keys) {
            Node _block = actors.get(key);
            if (_block != null)
                _blocks.add(_block);
        }
        return _blocks;
    }

    /**
     * Hide the block passed as a parameter with a cool animation.
     * Unsafe: due to performance reasons, we don't check if we're resizing an
     * actual block or another node. Nothing bad should happen, except a (possible)
     * visual glitch.
     * @param block the block to hide
     */
    public void hideBlock(Node block) {
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
     * Show the block passed as a parameter with a cool animation.
     * Unsafe: same reason as {@link #hideBlock(Node)}
     * @param block the block to show
     */
    public void showBlock(Node block) {
        Timeline scaleBlockTimeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(block.translateYProperty(), block.getTranslateY() + 1),
                        new KeyValue(block.scaleXProperty(), 0),
                        new KeyValue(block.scaleYProperty(), 0),
                        new KeyValue(block.scaleZProperty(), 0)
                ),
                new KeyFrame(
                        Duration.millis(500),
                        new KeyValue(block.translateYProperty(), block.getTranslateY()),
                        new KeyValue(block.scaleXProperty(), BLOCK_SCALE),
                        new KeyValue(block.scaleYProperty(), BLOCK_SCALE),
                        new KeyValue(block.scaleZProperty(), BLOCK_SCALE)
                )
        );
        scaleBlockTimeline.setCycleCount(1);
        scaleBlockTimeline.play();
    }

    /**
     * Move a worker with a cool animation.
     * @param playerNumber the number of the worker's player (0, 1, 2)
     * @param workerNumber the number of the worker (0 or 1)
     * @param position the target position
     */
    public void moveWorker(int playerNumber, int workerNumber, Point position) {
        Node worker = actors.get(getWorkerActorId(playerNumber, workerNumber));
        if (worker != null) {
            Point3D finalPosition = getSceneCoordinates(position).add(0, getWorkerHeight(position), 0),
                    diff = finalPosition.subtract(getSceneCoordinates(worker));
            Timeline xTimeline = new Timeline(
                        new KeyFrame(
                            Duration.millis(diff.getX() != 0 ? 250 : 0),
                            new KeyValue(worker.translateXProperty(), finalPosition.getX())
                        )
                    ),
                    yTimeline = new Timeline(
                        new KeyFrame(
                            Duration.millis(diff.getY() != 0 ? 250 : 0),
                            new KeyValue(worker.translateYProperty(), finalPosition.getY())
                        )
                    ),
                    zTimeline = new Timeline(
                        new KeyFrame(
                            Duration.millis(diff.getZ() != 0 ? 250 : 0),
                            new KeyValue(worker.translateZProperty(), finalPosition.getZ())
                        )
                    );
            xTimeline.setCycleCount(1);
            yTimeline.setCycleCount(1);
            zTimeline.setCycleCount(1);
            xTimeline.setOnFinished(event -> zTimeline.play());
            if(diff.getY() < 0) {
                yTimeline.setOnFinished(event -> xTimeline.play());
                yTimeline.play();
            } else {
                zTimeline.setOnFinished(event -> yTimeline.play());
                xTimeline.play();
            }
        }
    }

    private int getTowerSize(Point position) {
        int numOfBlocks = 0;
        for (int i = 0; i < 4; i++) {
            Node block = actors.get(getBlockId(position, i));
            if (block != null) numOfBlocks += 1;
        }

        return numOfBlocks;
    }

    private double getWorkerHeight(Point workerPos) {
        switch (getTowerSize(workerPos)) {
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

    private double getNewTowerHeight(Point towerPos) {
        switch (getTowerSize(towerPos)) {
            case 0:
                return BLOCK_Y_1;
            case 1:
                return BLOCK_Y_2;
            case 2:
                return BLOCK_Y_3;
            case 3:
                return BLOCK_Y_4;
            default:
                return 5;
        }
    }

    private void setNodeToPoint3D(Node node, Point3D point) {
        node.setTranslateX(point.getX());
        node.setTranslateY(point.getY());
        node.setTranslateZ(point.getZ());
    }

    public static String getBlockId(Point position, int height) {
        return "block" + position.getX() + position.getY() + height;
    }

    private void addToActorsAndRegister(String id, Node node) {
        actors.put(id, node);
        GUIMain.getRoot().getChildren().add(node);
    }

    public void incrementCell(Point position) {
        int height = getTowerSize(position);
        Node node = ActorFactory.getBlock(height);
        Point3D target = getSceneTowerCoordinates(position);
        setNodeToPoint3D(node, target);
        showBlock(node);
        addToActorsAndRegister(getBlockId(position, height), node);
    }

    /**
     * Transforms coordinates in 3D space to 2D-space integer coordinates on the board
     * @param v the Point3D
     * @return the coordinates with respect to the board
     */
    public static Point getBoardCoordinates(Point3D v) {
        // It just works, don't touch it LMAO
        double x = (v.getX() + 6) / 12 * 5;
        double y = -(v.getZ() - 6) / 12 * 5;

        return new Point((int) x, (int) y);
    }

    public static Point getBoardCoordinates(Node n) {
        return getBoardCoordinates(new Point3D(
                n.getTranslateX(),
                n.getTranslateY(),
                n.getTranslateZ()
        ));
    }

    public static Point3D getSceneCoordinates(Point v) {
        double x = 2.5 * (v.getX() - 2);
        double z = -2.5 * (v.getY() - 2);

        return new Point3D(x,0, z);
    }

    public Point3D getSceneWorkerCoordinates(Point point) {
        return getSceneCoordinates(point).add(0, getWorkerHeight(point), 0);
    }

    public Point3D getSceneTowerCoordinates(Point point) {
        return getSceneCoordinates(point).add(0, getNewTowerHeight(point), 0);
    }

    public static Point3D getSceneCoordinates(Node n) {
        return new Point3D(
                n.getTranslateX(),
                n.getTranslateY(),
                n.getTranslateZ()
        );
    }
}

class ActorFactory {
    private static final double BLOCK_SCALE = 0.35;

    private static int blockCounter = 0;
    static int workerCounter = 0;

    private static Mesh BLOCK_1_MESH = getMesh("/assets/BuildingBlock01.obj");
    private static Mesh BLOCK_2_MESH = getMesh("/assets/BuildingBlock02.obj");
    private static Mesh BLOCK_3_MESH = getMesh("/assets/BuildingBlock03.obj");
    private static Mesh DOME_MESH = getMesh("/assets/Dome.obj");
    private static Mesh WORKER_M_MESH = getMesh("/assets/MaleBuilder.obj");
    private static Mesh WORKER_F_MESH = getMesh("/assets/FemaleBuilder.obj");

    private static PhongMaterial BLOCK_1_MAT = getMaterial("/assets/BuildingBlock01_v001.png");
    private static PhongMaterial BLOCK_2_MAT = getMaterial("/assets/BuildingBlock02_v001.png");
    private static PhongMaterial BLOCK_3_MAT = getMaterial("/assets/BuildingBlock03_v001.png");
    private static PhongMaterial DOME_MAT = getColor(Color.MIDNIGHTBLUE);
    private static PhongMaterial WORKER_M_BLUE_MAT = getMaterial("/assets/MaleBuilder_Blue_v001.png");
    private static PhongMaterial WORKER_F_BLUE_MAT = getMaterial("/assets/FemaleBuilder_Blue_v001.png");
    private static PhongMaterial WORKER_M_ORANGE_MAT = getMaterial("/assets/MaleBuilder_Orange_v001.png");
    private static PhongMaterial WORKER_F_ORANGE_MAT = getMaterial("/assets/FemaleBuilder_Orange_v001.png");
    private static PhongMaterial WORKER_M_PINK_MAT = getMaterial("/assets/MaleBuilder_Pink_v001.png");
    private static PhongMaterial WORKER_F_PINK_MAT = getMaterial("/assets/FemaleBuilder_Pink_v001.png");

    static Mesh getMesh(String meshUrl) {
        // Import 3D resource
        URL modelUrl = ActorFactory.class.getResource(meshUrl);
        ObjModelImporter objImporter = new ObjModelImporter();

        objImporter.read(modelUrl);
        MeshView[] meshViews = objImporter.getImport();
        objImporter.close();

        // Get the first mesh (there should not be multiple meshes)
        MeshView meshView = meshViews[0];
        meshView.setCullFace(CullFace.BACK);

        return meshView.getMesh();
    }

    static PhongMaterial getColor(Color color) {
        PhongMaterial texturedMaterial = new PhongMaterial();
        texturedMaterial.setDiffuseColor(color);
        return texturedMaterial;
    }

    static PhongMaterial getMaterial(String textureUrl) {
        // This looks up to /resources/
        Image texture = new Image(textureUrl);
        PhongMaterial texturedMaterial = new PhongMaterial();
        texturedMaterial.setDiffuseMap(texture);

        return texturedMaterial;
    }

    public static Node getBlock(int height) {
        MeshView block = new MeshView();
        switch (height) {
            case 0:
                block.setMesh(BLOCK_1_MESH);
                block.setMaterial(BLOCK_1_MAT);
                break;
            case 1:
                block.setMesh(BLOCK_2_MESH);
                block.setMaterial(BLOCK_2_MAT);
                break;
            case 2:
                block.setMesh(BLOCK_3_MESH);
                block.setMaterial(BLOCK_3_MAT);
                break;
            case 3:
                block.setMesh(DOME_MESH);
                block.setMaterial(DOME_MAT);
            default:
                return null;
        }
        // Fill triangles
        block.setDrawMode(DrawMode.FILL);

        // Set position
        block.setTranslateX(0);
        block.setTranslateY(10);
        block.setTranslateZ(0);

        // Set scale (unified)
        block.setScaleX(BLOCK_SCALE);
        block.setScaleY(BLOCK_SCALE);
        block.setScaleZ(BLOCK_SCALE);

        return block;
    }

    public static Node getWorker(int playerNumber, int workerNumber) {
        MeshView worker = new MeshView();
        // Assign Mesh
        switch (workerNumber) {
            case 0:
                worker.setMesh(WORKER_M_MESH);
                break;
            case 1:
                worker.setMesh(WORKER_F_MESH);
        }
        // Assign Texture
        switch (playerNumber) {
            case 0:
                worker.setMaterial(workerNumber == 0 ? WORKER_M_BLUE_MAT : WORKER_F_BLUE_MAT);
                break;
            case 1:
                worker.setMaterial(workerNumber == 0 ? WORKER_M_ORANGE_MAT : WORKER_F_ORANGE_MAT);
                break;
            case 2:
                worker.setMaterial(workerNumber == 0 ? WORKER_M_PINK_MAT : WORKER_F_PINK_MAT);
                break;
        }
        // Fill triangles
        worker.setDrawMode(DrawMode.FILL);

        // Set position
        worker.setTranslateX(0);
        worker.setTranslateY(10);
        worker.setTranslateZ(0);

        // Set scale (unified)
        worker.setScaleX(1);
        worker.setScaleY(1);
        worker.setScaleZ(1);

        return worker;
    }
}