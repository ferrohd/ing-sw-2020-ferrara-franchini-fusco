package it.polimi.ingsw.PSP14.client.model;

import it.polimi.ingsw.PSP14.client.view.cli.GodFactory;
import it.polimi.ingsw.PSP14.client.view.cli.UIGod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class GodFactoryTest {

    @Test
    void getGodModel() {
        GodFactory factory;
        try {
            factory = new GodFactory("src/main/resources/gods/godlist.xml");
        } catch(Exception e) {
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
}