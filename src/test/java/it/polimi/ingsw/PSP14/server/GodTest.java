package it.polimi.ingsw.PSP14.server;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import it.polimi.ingsw.PSP14.core.gods.God;
import it.polimi.ingsw.PSP14.core.gods.GodControllerFactory;
import it.polimi.ingsw.PSP14.core.GodNotFoundException;

public class GodTest {

    @Test
    void factoryShouldNotThrow() {
        assertDoesNotThrow(() -> {
            God testGod = GodControllerFactory.getController("Apollo");
            testGod.onMovePhase(); // Overridden
            testGod.onBuildPhase(); // Not overridden
        });
    }

    @Test
    void factoryShouldThrow() {
        assertThrows(GodNotFoundException.class, () -> GodControllerFactory.getController("Shiva"));
    }
}
