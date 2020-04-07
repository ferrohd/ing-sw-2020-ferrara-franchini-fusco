package it.polimi.ingsw.PSP14.server;

import it.polimi.ingsw.PSP14.core.controller.gods.GodController;
import it.polimi.ingsw.PSP14.core.controller.gods.GodControllerFactory;
import it.polimi.ingsw.PSP14.core.model.GodNotFoundException;
import it.polimi.ingsw.PSP14.core.model.PlayerNotFoundException;
import it.polimi.ingsw.PSP14.server.match.MatchModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

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
