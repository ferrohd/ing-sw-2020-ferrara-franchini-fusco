package it.polimi.ingsw.PSP14.client.view;

import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;
import it.polimi.ingsw.PSP14.server.model.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class UI {
    private UICache cache;
    private Map<String, Color> colorMap;

    public UI() {
        cache = new UICache();
        colorMap = new HashMap<>();
    }

    protected UICache getCache() { return cache; }
    protected Map<String, Color> getColorMap() { return colorMap; }

    public abstract void update();
    abstract Color getColor();

    /**
     * @param newPlayer new player to be registered in the view
     */
    public void registerPlayer(String newPlayer) {
        colorMap.put(newPlayer, getColor());
    }

    /**
     * @param removedPlayer existing player to be removed from the view
     */
    public void removePlayer(String removedPlayer) {
        colorMap.remove(removedPlayer);
    }

    public void drawWorkerSet(Point pos, String player) {
        cache.getBlock(pos).setWorker(player);
    }

    public void drawWorkerUnset(Point pos) {
        cache.getBlock(pos).unsetWorker();
    }

    public void drawWorkerMove(Point oldPos, Point newPos) {
        String player = cache.getBlock(oldPos).getWorker();
        drawWorkerUnset(oldPos);
        drawWorkerSet(newPos, player);
    }

    public void drawBlockAdd(Point pos) {
        cache.getBlock(pos).incrementSize();
    }

    public void drawBlockRemove(Point pos) {
        cache.getBlock(pos).decrementSize();
    }

    public void drawDomeAdd(Point pos) {
        cache.getBlock(pos).setDome();
    }

    public void drawDomeRemove(Point pos) {
        cache.getBlock(pos).unsetDome();
    }

    public abstract void welcome();

    public abstract void noticeConnecting(String hostname, int port);

    public abstract int getRoomSize();

    public abstract void notice(String s);

    public abstract String askUsername();

    public abstract int chooseFirstPlayer(List<PlayerProposal> proposals);

    public abstract int chooseWorker();
}
