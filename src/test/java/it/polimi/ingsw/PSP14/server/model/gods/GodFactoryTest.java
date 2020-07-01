package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.gods.GodFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GodFactoryTest {
    @Test
    public void allGodsAreCreatedCorrectly() {
        String[] godNames = {
                "Apollo",
                "Artemis",
                "Athena",
                "Atlas",
                "Demeter",
                "Hephaestus",
                "Minotaur",
                "Pan",
                "Prometheus",
                "Chronus",
                "Hestia",
                "Poseidon",
                "Triton",
                "Zeus"};

        for (String name : godNames) {
            God god = GodFactory.getGod(name, "Ada");
            assertEquals("it.polimi.ingsw.PSP14.server.model.gods." + name, god.getClass().getName());
        }
    }
}