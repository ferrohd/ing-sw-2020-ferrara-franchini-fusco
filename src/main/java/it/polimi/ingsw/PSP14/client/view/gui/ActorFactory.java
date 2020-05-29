package it.polimi.ingsw.PSP14.client.view.gui;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;

import java.net.URL;

public class ActorFactory {
    private static final double BLOCK_SCALE = 0.35;

    private static final int blockCounter = 0;
    static int workerCounter = 0;

    private static final Mesh BLOCK_1_MESH = getMesh("/assets/BuildingBlock01.obj");
    private static final Mesh BLOCK_2_MESH = getMesh("/assets/BuildingBlock02.obj");
    private static final Mesh BLOCK_3_MESH = getMesh("/assets/BuildingBlock03.obj");
    private static final Mesh DOME_MESH = getMesh("/assets/Dome.obj");
    private static final Mesh WORKER_M_MESH = getMesh("/assets/MaleBuilder.obj");
    private static final Mesh WORKER_F_MESH = getMesh("/assets/FemaleBuilder.obj");

    private static final PhongMaterial BLOCK_1_MAT = getMaterial("/assets/BuildingBlock01_v001.png");
    private static final PhongMaterial BLOCK_2_MAT = getMaterial("/assets/BuildingBlock02_v001.png");
    private static final PhongMaterial BLOCK_3_MAT = getMaterial("/assets/BuildingBlock03_v001.png");
    private static final PhongMaterial DOME_MAT = getColor(Color.MIDNIGHTBLUE);
    private static final PhongMaterial WORKER_M_BLUE_MAT = getMaterial("/assets/MaleBuilder_Blue_v001.png");
    private static final PhongMaterial WORKER_F_BLUE_MAT = getMaterial("/assets/FemaleBuilder_Blue_v001.png");
    private static final PhongMaterial WORKER_M_ORANGE_MAT = getMaterial("/assets/MaleBuilder_Orange_v001.png");
    private static final PhongMaterial WORKER_F_ORANGE_MAT = getMaterial("/assets/FemaleBuilder_Orange_v001.png");
    private static final PhongMaterial WORKER_M_PINK_MAT = getMaterial("/assets/MaleBuilder_Pink_v001.png");
    private static final PhongMaterial WORKER_F_PINK_MAT = getMaterial("/assets/FemaleBuilder_Pink_v001.png");

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
                break;
            default:
                throw new IndexOutOfBoundsException(height + " should be in range 0 - 3");
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

