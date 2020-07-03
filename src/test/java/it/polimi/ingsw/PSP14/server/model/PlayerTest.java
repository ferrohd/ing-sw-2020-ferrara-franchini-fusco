package it.polimi.ingsw.PSP14.server.model;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.board.Direction;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchController;
import it.polimi.ingsw.PSP14.server.model.gods.God;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Player testPlayer;

    @BeforeEach
    void setUp() throws IOException {
        testPlayer = new Player("Ada", new God("Ada"), new MatchController());
        testPlayer.setWorker(0, new Point(0, 0));
    }

    @Test
    void playerShouldInstantiate() {
        assertEquals("Ada", testPlayer.getUsername());
    }

    @Test
    void moveWorker() throws IOException {
        testPlayer.moveWorker(0, Direction.N);
        assertTrue(testPlayer.getWorkerPos(0).equals(new Point(0, 1)));
    }

    @Test
    void getWorker() {
        assertTrue(testPlayer.getWorkerPos(0).equals(new Point(0, 0)));
    }

    @Test
    void getGod() {
        assertNotEquals(null, testPlayer.getGod());
    }

    @Test
    public void shouldRemove() throws IOException {
        FakeMatchController controller = new FakeMatchController() {
            @Override
            public void notifyUnregisterPlayer(String player) throws IOException {
                assertEquals("Ada", player);
                flag = true;
            }
        };
        testPlayer = new Player("Ada", new God("Ada"), controller);
        testPlayer.clear();
        assertTrue(controller.flag);
    }
}
