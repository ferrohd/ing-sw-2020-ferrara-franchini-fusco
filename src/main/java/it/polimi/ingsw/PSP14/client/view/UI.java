package it.polimi.ingsw.PSP14.client.view;

import it.polimi.ingsw.PSP14.core.model.Cell;
import it.polimi.ingsw.PSP14.core.Player;
import it.polimi.ingsw.PSP14.core.model.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class UI {
    protected UICache cache;
    protected Map<String, Color> colorMap;

    public UI() {
        cache = new UICache();
        colorMap = new HashMap<>();
    }

    /**
     * @param newPlayer new player to be registered in the view
     */
    public void registerPlayer(String newPlayer) {
        colorMap.put(newPlayer, new Color(0, 0, 0));
    }

    /**
     * @param removedPlayer existing player to be removed from the view
     */
    public void removePlayer(String removedPlayer) {
        colorMap.remove(removedPlayer);
    }

    public void drawWorkerSet(Point pos, String player) {
        cache.setWorker(pos, player);
    }

    public void drawWorkerUnset(Point pos) {
        cache.unsetWorker(pos);
    }

    public void drawWorkerMove(Point oldPos, Point newPos) {
        String player = cache.getWorker(oldPos);
        drawWorkerUnset(oldPos);
        drawWorkerSet(newPos, player);
    }

    public void drawBlockAdd(Point pos) {
        cache.incrementSize(pos);
    }

    public void drawBlockRemove(Point pos) {
        cache.decrementSize(pos);
    }

    public void drawDomeAdd(Point pos) {
        cache.setDome(pos);
    }

    public void drawDomeRemove(Point pos) {
        cache.unsetDome(pos);
    }
}
