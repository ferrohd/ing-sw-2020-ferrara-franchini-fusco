package it.polimi.ingsw.PSP14.server.model.gods;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GodTest {
    @Test
    void factoryShouldNotThrow() {
        assertDoesNotThrow(() -> {
            God testGod = GodFactory.getGod("Apollo", "Ada");
        });
    }

    @Test
    void factoryShouldNotThrowOnMissingGod() {
        assertDoesNotThrow(() -> GodFactory.getGod("Shiva", "prova"));
    }

    // If the previous test pass
    God testGod = GodFactory.getGod("XXX", "Ada");

    @Test
    void getOwner() {
        assertEquals(testGod.getOwner(), "Ada");
    }
}