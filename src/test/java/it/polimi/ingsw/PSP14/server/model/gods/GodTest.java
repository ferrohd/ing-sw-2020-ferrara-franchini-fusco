package it.polimi.ingsw.PSP14.server.model.gods;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class GodTest {

    @Test
    void factoryShouldNotThrow() {
        assertDoesNotThrow(() -> {
            God testGod = GodFactory.getGod("Apollo", "prova");
        });
    }

    @Test
    void factoryShouldNotThrowOnMissingGod() {
        assertDoesNotThrow(() -> GodFactory.getGod("Shiva", "prova"));
    }
}
