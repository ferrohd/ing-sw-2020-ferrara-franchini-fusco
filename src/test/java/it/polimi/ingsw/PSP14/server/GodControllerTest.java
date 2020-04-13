package it.polimi.ingsw.PSP14.server;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import it.polimi.ingsw.PSP14.core.gods.GodController;
import it.polimi.ingsw.PSP14.core.gods.GodControllerFactory;
import it.polimi.ingsw.PSP14.core.GodNotFoundException;

public class GodControllerTest {

    @Test
    void factoryShouldNotThrow() {
        assertDoesNotThrow(() -> {
            GodController testGod = GodControllerFactory.getController("Apollo");
            testGod.onMovePhase(); // Overridden
            testGod.onBuildPhase(); // Not overridden
        });
    }

    @Test
    void factoryShouldThrow() {
        assertThrows(GodNotFoundException.class, () -> GodControllerFactory.getController("Shiva"));
    }
}
