package it.polimi.ingsw.PSP14.server.model;

import it.polimi.ingsw.PSP14.server.model.board.Direction;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.gods.God;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Player testPlayer;

    @BeforeEach
    void setUp() {
        testPlayer = new Player("Ada", new God("Ada"));
        testPlayer.setWorker(0, new Point(0,0));
    }

    @Test
    void playerShouldInstantiate() {
        assertDoesNotThrow(() -> {
            testPlayer = new Player("Ada", new God("Ada"));
        });
        assertEquals("Ada", testPlayer.getUsername());
    }

    @Test
    void playerShouldNotInstantiate() {
        assertThrows(NullPointerException.class, () -> {
            testPlayer = new Player(null, new God("Ada"));
        });
    }

//    @Test
//    void getWorkerShouldFailOutOfBounds() {
//        assertThrows(NullPointerException.class, () -> {
//            testPlayer.getWorker(3);
//        });
//    }

    @Test
    void moveWorker() {
        testPlayer.moveWorker(0, Direction.N);
        assertTrue(testPlayer.getWorker(0).getPos().equals(new Point(0,1)));
    }

    @Test
    void getWorker() {
        assertTrue(testPlayer.getWorker(0).getPos().equals(new Point(0,0)));
    }

    @Test
    void getGod() {
        assertNotEquals(null, testPlayer.getGod());
    }
}
