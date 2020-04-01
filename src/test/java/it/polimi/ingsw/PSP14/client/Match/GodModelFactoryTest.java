package it.polimi.ingsw.PSP14.client.Match;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GodModelFactoryTest {

    @Test
    void getGodModel() {
        GodModelFactory factory;
        try {
            factory = new GodModelFactory("src/main/resources/gods/gods_list.xml");
        } catch(Exception e) {
            e.printStackTrace();
            fail("Exception thrown");
            return;
        }

        GodModel atlas = factory.getGodModel("Atlas");
        assertEquals(atlas.getName(), "Atlas");
        assertEquals(atlas.getAlias(), "Titan Shouldering the Heavens");
        assertEquals(atlas.getAbility(), "Your Build");
        assertEquals(atlas.getDescription(), "Your Worker may build a dome at any level.");
    }
}