package it.polimi.ingsw.PSP14.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import it.polimi.ingsw.PSP14.server.model.Player;
import it.polimi.ingsw.PSP14.server.model.gods.God;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    Player testPlayer;

    @Test
    void playerShouldInstantiate() {
        assertDoesNotThrow(() -> {
            testPlayer = new Player("Ada", new God());
        });
        assertEquals("Ada", testPlayer.getUsername());
        assertNotEquals(null, testPlayer.getColor());
    }

    @Test
    void playerShouldNotInstantiate() {
        assertThrows(NullPointerException.class, () -> {
            testPlayer = new Player(null, new God());
        });
    }

    @Test
    void getWorkerShouldFailOutOfBounds() {
        assertThrows(NullPointerException.class, () -> {
            testPlayer.getWorker(3);
        });
    }
}
