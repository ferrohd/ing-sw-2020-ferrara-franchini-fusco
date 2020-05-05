package it.polimi.ingsw.PSP14.client.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UIPlayerTest {

    UIPlayer player;

    @BeforeEach
    void setUp() {
        player = new UIPlayer("Ada", 0,null);
    }

    @Test
    void getUsername() {
        assertEquals("Ada", player.getUsername());
    }

    @Test
    void getColor() {
        assertNull(player.getColor());
    }

    @Test
    void setGod() {
        player.setGod("Apollo");
        assertNotNull(player.getGod());
        assertEquals("Apollo", player.getGod().getName());
    }

    @Test
    void getWorkers() {
        assertEquals(0, player.getWorkers().size());
    }

    @Test
    void getWorker() {
        player.setWorker(new UIWorker(0, player));
        assertNotNull(player.getWorker(0));
    }

    @Test
    void unsetWorker() {
        player.setWorker(new UIWorker(0, player));
        assertNotNull(player.getWorker(0));
        player.unsetWorker(0);
        assertNull(player.getWorker(0).getCell());
    }

    @Test
    void removeWorker() {
        player.setWorker(new UIWorker(0, player));
        assertNotNull(player.getWorker(0));
        player.removeWorker(0);
        assertNull(player.getWorker(0));
    }

    @Test
    void testRemoveWorker() {
        UIWorker _w = new UIWorker(0, player);
        player.setWorker(_w);
        assertNotNull(player.getWorker(0));
        player.removeWorker(_w);
        assertNull(player.getWorker(0));
    }
}