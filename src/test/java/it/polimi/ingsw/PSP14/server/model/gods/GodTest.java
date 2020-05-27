package it.polimi.ingsw.PSP14.server.model.gods;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
