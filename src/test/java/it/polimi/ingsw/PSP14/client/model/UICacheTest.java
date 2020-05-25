package it.polimi.ingsw.PSP14.client.model;

import it.polimi.ingsw.PSP14.client.view.cli.UICache;
import it.polimi.ingsw.PSP14.client.view.cli.UIWorker;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UICacheTest {

    UICache cache;

    @BeforeEach
    void setUp() {
        cache = new UICache();
        cache.addPlayer("Ada",0 , null);
    }

    @Test
    void getCellWithPoint() {
        assertEquals(0, cache.getCell(new Point(0,0)).getTowerHeight());
    }

    @Test
    void getCellWithCoordinates() {
        assertEquals(0, cache.getCell(0,0).getTowerHeight());
    }

    @Test
    void addPlayer() {
        cache.addPlayer("Bob", 1, null);
        assertEquals("Bob", cache.getPlayer("Bob").getUsername());
    }

    @Test
    void removePlayer() {
        cache.removePlayer("Ada");
        assertEquals(0, cache.getPlayers().size());
    }

    @Test
    void setWorker() {
        UIWorker worker = new UIWorker(0, cache.getPlayer("Ada"));
        cache.setWorker(worker, "Ada", cache.getCell(0,0));
        assertEquals(worker, cache.getCell(0,0).getWorker());
        assertEquals(worker, cache.getPlayer("Ada").getWorker(0));
        assertEquals(cache.getCell(0,0).getWorker().getCell(), cache.getCell(0,0));
        assertEquals(cache.getPlayer("Ada").getWorker(0).getPlayer(), cache.getPlayer("Ada"));
    }

    @Test
    void unsetWorker() {
        UIWorker worker = new UIWorker(0, cache.getPlayer("Ada"));
        cache.setWorker(worker, "Ada", cache.getCell(0,0));
        cache.unsetWorker(worker);
        assertNull(cache.getPlayer("Ada").getWorker(0).getCell());
        assertNull(cache.getCell(0,0).getWorker());
        assertNotNull(cache.getPlayer("Ada").getWorker(0));
    }
}