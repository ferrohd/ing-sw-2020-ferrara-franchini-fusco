package it.polimi.ingsw.PSP14.client.view.gui;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class GameSceneModel {
    private static final double WALLS_X = 8.65;
    private static final double WALLS_SCALE = 1;
    private static final double WALLS_Y = 3.5;
    private static final double WALLS_Z = 8.65;
    private static final double BLOCK_X = -0.3;
    private static final double BLOCK_Z = -0.1;
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
    private static final double SELECT_HEIGHT = 0.4;
    private static final double SELECT_Y_1 = 0 - SELECT_HEIGHT/2; // ground
    private static final double SELECT_Y_2 = -1.5 - SELECT_HEIGHT/2; // lv 1
    private static final double SELECT_Y_3 = -2.5 - SELECT_HEIGHT/2; // lv 2
    private static final double SELECT_Y_4 = -3.5 - SELECT_HEIGHT/2; // lv 3

    // Contains all actors, accessible with an ID
    private final Map<String, Node> actors = new HashMap<>();
    private final Group root = new Group();

    public GameSceneModel() {
        setupScenery();
    }

    public Group getRoot() { return root; }

    /**
     * Init the scenery (sea, board, cliff, all the cosmetic stuff).
     */
    public void setupScenery() {
        addActor("sea", "/assets/Sea.obj", "/assets/Sea.png", new Point3D(0, 3, 0), 1);
        addActor("cliff", "/assets/Cliff.obj", "/assets/Cliff_v001.png", new Point3D(-0.1, 1.4, 0), 7.9);
        addActor("board", "/assets/Board.obj", "/assets/Cliff_v001.png", new Point3D(0, 0, 0), 1);
        addActor("outerWall", "/assets/OuterWall.obj", "/assets/Cliff_v001.png", new Point3D(WALLS_X, WALLS_Y, WALLS_Z), WALLS_SCALE);
        addActor("innerWall", "/assets/InnerWalls.obj", "/assets/Cliff_v001.png", new Point3D(WALLS_X, WALLS_Y, WALLS_Z), WALLS_SCALE);

        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(Color.LIGHTYELLOW);
        actors.put("light", ambientLight);
        root.getChildren().add(ambientLight);
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
        root.getChildren().add(meshView);
        return meshView;
    }

    /**
     * Get an actor (Node) from the actors data structure
     * @param id the key of that actor
     * @return the Node of that actor
     */
    public Node getActorById(String id) {
        return actors.get(id);
    }

    /**
     * Get a worker actor id.
     * @param player the worker's player number
     * @param workerId the worker's number
     * @return a key (String) representing that worker
     */
    public static String getWorkerActorId(int player, int workerId) {
        return "worker" + player + workerId;
    }

    /**
     * Add a worker to the board.
     * @param point a model coordinate
     * @param workerId the worker id number
     * @param playerId the player id number
     */
    public void addWorker(Point point, int workerId, int playerId) {
        Node worker = ActorFactory.getWorker(playerId, workerId);
        Point3D target = getSceneWorkerCoordinates(point);
        setNodeToPoint3D(worker, target);
        addToActorsAndRegister(getWorkerActorId(playerId, workerId), worker);
    }

    /**
     * Remove a worker from the game.
     * @param workerId the worker id number
     * @param playerId the player id number
     */
    public void removeWorker(int workerId, int playerId) {
        Node worker = actors.get(getWorkerActorId(playerId, workerId));
        actors.remove(getWorkerActorId(playerId, workerId));
        root.getChildren().remove(worker);
    }

    /**
     * Get a worker by passing the player it belongs to and the id of the worker.
     * @param player the player number
     * @param worker the worker number
     * @return that worker's node reference
     */
    public Node getWorker(int player, int worker) {
        return actors.get(getWorkerActorId(player, worker));
    }

    /**
     * Return a collection of nodes representing all the workers
     * in the game.
     * @return a collection of nodes
     */
    public List<Node> getAllWorkers() {
        return actors.keySet().stream()
                .filter(k -> k.startsWith("worker"))
                .map(actors::get)
                .collect(Collectors.toList());
    }

    /**
     * Return an ordered list of points representing all the workers
     * of a certain player.
     * @return a list of points
     */
    public List<Point> getAllPlayerWorkers(int playerId) {
        List<Point> points = new ArrayList<>();
        for(int i = 0; i < 2; ++i) {
            points.add(getBoardCoordinates(actors.get("worker"+playerId+i)));
        }

        return points;
    }

    /**
     * Return a list of nodes representing all the building components
     * in the game.
     * @return a collection of nodes
     */
    public List<Node> getAllBlocks() {
        return actors.keySet().stream()
                .filter(k -> k.startsWith("block"))
                .map(actors::get)
                .collect(Collectors.toList());
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
    public void moveWorker(int playerNumber, int workerNumber, Point position, CountDownLatch latch) {
        moveWorkerNode(position, actors.get(getWorkerActorId(playerNumber, workerNumber)), latch);
    }

    /**
     * Move a worker to a target position with a cool animation.
     * @param position the target position
     * @param worker the worker to move
     */
    private void moveWorkerNode(Point position, Node worker, CountDownLatch latch) {
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
                zTimeline.setOnFinished(event -> latch.countDown());
                yTimeline.play();
            } else {
                zTimeline.setOnFinished(event -> yTimeline.play());
                yTimeline.setOnFinished(event -> latch.countDown());
                xTimeline.play();
            }
        } else {
            latch.countDown();
        }
    }

    /**
     * Get a tower height in model "numbers".
     * @param position the tower position in model coordinates
     * @return the height of the tower
     */
    private int getTowerHeight(Point position) {
        int numOfBlocks = 0;
        for (int i = 0; i < 4; i++) {
            Node block = actors.get(getBlockId(position, i));
            if (block != null) numOfBlocks += 1;
        }

        return numOfBlocks;
    }

    /**
     * Get a preset coordinate for a worker height
     * @param workerPos a model coordinate for the worker
     * @return the world Y coordinate for that worker
     */
    private double getWorkerHeight(Point workerPos) {
        switch (getTowerHeight(workerPos)) {
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

    /**
     * Get a preset coordinate for a tower height
     * @param towerPos a model coordinate for the tower
     * @return the world Y coordinate for that tower
     */
    private double getNewTowerHeight(Point towerPos) {
        switch (getTowerHeight(towerPos)) {
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

    /**
     * Set a node to a target world position
     * @param node the node
     * @param point the target position
     */
    private void setNodeToPoint3D(Node node, Point3D point) {
        node.setTranslateX(point.getX());
        node.setTranslateY(point.getY());
        node.setTranslateZ(point.getZ());
    }

    /**
     * Get the block ID string.
     * @param position of the block
     * @param height of the block
     * @return the unique block label
     */
    public static String getBlockId(Point position, int height) {
        return "block" + position.getX() + position.getY() + height;
    }

    /**
     * Keep track of a node by binding it with an ID
     * @param id the ID of the node
     * @param node the node
     */
    private void addToActorsAndRegister(String id, Node node) {
        node.setId(id);
        actors.put(id, node);
        root.getChildren().add(node);
    }

    /**
     * Add a tower block on a tower.
     * @param position the position of the tower.
     */
    public void incrementCell(Point position) {
        setTower(position, getTowerHeight(position));
        updateWorkers();
        updateSelectables();
    }

    /**
     * Updates the worker position, to prevent them to glitching into
     * towers. It should get called when a cell is updated.
     */
    private void updateWorkers() {
        for(Node w : getAllWorkers()) {
            Point p = getBoardCoordinates(w);
            moveWorkerNode(p, w, new CountDownLatch(1));
        }
    }

    /**
     * Updates the selectable position, to prevent them to glitching into
     * towers. It should get called when a cell is updated.
     */
    private void updateSelectables() {
        for(Node w : getAllSelectables()) {
            Point p = getBoardCoordinates(w);
            moveSelectableNode(p, w);
        }
    }

    /**
     * Put a dome on the cell, "filling" the tower (setting it to height 3).
     * WARN: Chronus + Atlas (might) cause graphic glitches.
     * @param position the model position of the dome
     */
    public void putDome(Point position) {
        while(getTowerHeight(position) <= 3)
            setTower(position, getTowerHeight(position));
    }

    /**
     * Build a tower in the specified model position at the specified height.
     * @param position the model position of the tower
     * @param height the height of the tower
     */
    private void setTower(Point position, int height) {
        Node tower = ActorFactory.getBlock(height);
        Point3D target = getSceneTowerCoordinates(position);
        // Fix padding
        target = target.add(-BLOCK_X, 0, BLOCK_Z);
        setNodeToPoint3D(tower, target);
        showBlock(tower);
        addToActorsAndRegister(getBlockId(position, height), tower);
    }

    public List<Point> getWorkerPositions() {
        return getAllWorkers().stream().map(GameSceneModel::getBoardCoordinates).collect(Collectors.toList());
    }

    /**
     * Creates a rect at target position
     * @param targetPos where to spawn the rect
     */
    public void addSelectable(int index, Point targetPos) {
        Node rect = ActorFactory.getSelectable();

        setNodeToPoint3D(rect, getSceneSelectableCoordinates(targetPos));
        addToActorsAndRegister("sel" + index, rect);
    }

    /**
     * Render a list of points (choices) as selectables.
     * @param positions a list of points
     */
    public void addAllSelectables(List<Point> positions) {
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i) != null) addSelectable(i, positions.get(i));
        }
    }

    /**
     * Remove all rendered selectables from the scene.
     */
    public void removeAllSelectables() {
       actors.keySet().stream()
                .filter(k -> k.startsWith("sel"))
                .map(actors::get)
                .forEach(a -> root.getChildren().remove(a));
    }

    /**
     * Move a selectable node adjusting its height
     * @param p target position
     * @param n the node of the selectable
     */
    private void moveSelectableNode(Point p, Node n) {
        Point3D finalPosition = getSceneCoordinates(p).add(0, getSelectableHeight(p), 0);
        setNodeToPoint3D(n, finalPosition);
    }

    /**
     * Get a preset coordinate for a selectable height
     * @param towerPos a model coordinate for the selectable
     * @return the world Y coordinate for that selectable
     */
    private double getSelectableHeight(Point towerPos) {
        switch (getTowerHeight(towerPos)) {
            case 0:
                return SELECT_Y_1;
            case 1:
                return SELECT_Y_2;
            case 2:
                return SELECT_Y_3;
            case 3:
                return SELECT_Y_4;
            default:
                return 5;
        }
    }

    public List<Node> getAllSelectables() {
        return actors.keySet().stream()
                .filter(k -> k.startsWith("sel"))
                .sorted()
                .map(actors::get)
                .collect(Collectors.toList());
    }

    /**
     * Transforms coordinates in world space to model coordinates.
     * @param v the world coordinates
     * @return the model coordinates
     */
    public static Point getBoardCoordinates(Point3D v) {
        // It just works, don't touch it LMAO
        double x = (v.getX() + 6) / 12 * 5;
        double y = -(v.getZ() - 6) / 12 * 5;

        return new Point((int) x, (int) y);
    }

    /**
     * Get model coordinates from a node
     * @param n a node
     * @return the position of the node in model coordinates
     */
    public static Point getBoardCoordinates(Node n) {
        return getBoardCoordinates(new Point3D(
                n.getTranslateX(),
                n.getTranslateY(),
                n.getTranslateZ()
        ));
    }

    /**
     * Get world coordinates from Point (2D).
     * @param v a point from the model
     * @return the point in world coordinates
     */
    public static Point3D getSceneCoordinates(Point v) {
        double x = 2.5 * (v.getX() - 2);
        double z = -2.5 * (v.getY() - 2);

        return new Point3D(x,0, z);
    }

    /**
     * Get the coordinates to place a worker from a Point (2D).
     * @param point the point from the model
     * @return new worker coordinates
     */
    public Point3D getSceneWorkerCoordinates(Point point) {
        return getSceneCoordinates(point).add(0, getWorkerHeight(point), 0);
    }

    /**
     * Get the coordinates to place a tower block from a Point (2D).
     * The returned coordinates contain the updated Y axis, to place the block of
     * the tower at the correct height.
     * @param point the point from the model
     * @return new tower block coordinates
     */
    public Point3D getSceneTowerCoordinates(Point point) {
        return getSceneCoordinates(point).add(0, getNewTowerHeight(point), 0);
    }

    /**
     * Get the coordinates to place a worker from a Point (2D).
     * @param point the point from the model
     * @return new worker coordinates
     */
    public Point3D getSceneSelectableCoordinates(Point point) {
        return getSceneCoordinates(point).add(0, getSelectableHeight(point), 0);
    }

    /**
     * Get the coordinates of a generic Node with a Point3D.
     * @param n the node
     * @return a Point3D of the coordinates of the node n
     */
    public static Point3D getSceneCoordinates(Node n) {
        return new Point3D(
                n.getTranslateX(),
                n.getTranslateY(),
                n.getTranslateZ()
        );
    }
}