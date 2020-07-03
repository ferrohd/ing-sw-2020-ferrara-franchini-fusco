package it.polimi.ingsw.PSP14.client.model;

import it.polimi.ingsw.PSP14.client.view.cli.GodFactory;
import it.polimi.ingsw.PSP14.client.view.cli.UIGod;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class GodFactoryTest {

    @Test
    void testSingleGodProperties() {
        GodFactory factory;
        try {
            factory = GodFactory.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown");
            return;
        }

        UIGod atlas = factory.getGod("Atlas");
        assertEquals(atlas.getName(), "Atlas");
        assertEquals(atlas.getAlias(), "Titan Shouldering the Heavens");
        assertEquals(atlas.getAbility(), "Your Build");
        assertEquals(atlas.getDescription(), "Your Worker may build a dome at any level.");
    }

    @Test
    void testGodNames() {
        GodFactory factory;
        try {
            factory = GodFactory.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown");
            return;
        }

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


        assertEquals(new HashSet<>(Arrays.asList(godNames)), factory.getGodNames());
    }
}