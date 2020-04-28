package it.polimi.ingsw.PSP14.server.model.gods;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import it.polimi.ingsw.PSP14.server.model.GodNotFoundException;
import org.junit.jupiter.api.Test;

import it.polimi.ingsw.PSP14.server.model.gods.God;
import it.polimi.ingsw.PSP14.server.model.gods.GodControllerFactory;

public class GodTest {

    @Test
    void factoryShouldNotThrow() {
        assertDoesNotThrow(() -> {
            God testGod = GodControllerFactory.getController("Apollo", "prova");
        });
    }

    @Test
    void factoryShouldThrow() {
        assertThrows(GodNotFoundException.class, () -> GodControllerFactory.getController("Shiva", "prova"));
    }
}
