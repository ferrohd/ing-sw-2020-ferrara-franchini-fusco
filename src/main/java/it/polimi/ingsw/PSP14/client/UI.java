package it.polimi.ingsw.PSP14.client;

import it.polimi.ingsw.PSP14.core.model.Cell;
import it.polimi.ingsw.PSP14.core.Player;
import it.polimi.ingsw.PSP14.core.model.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public abstract class   UI {

    ArrayList<Player> players;

    public UI(Collection<Player> players) {
        this.players.addAll(players);
    }

    /**
     * @param newPlayer new player to be registered in the view
     */
    public void registerPlayer(Player newPlayer) {
        players.add(newPlayer);
    }

    /**
     * @param removedPlayer existing player to be removed from the view
     */
    public void removePlayer(Player removedPlayer) {
        players.remove(removedPlayer);
    }

    public abstract void drawBoardInit(Cell[][] board);
    public abstract void drawWorkerAdd(Point newPos);
    public abstract void drawWorkerRemove(Point oldPos);
    public abstract void drawWorkerMove(Point oldPos, Point newPos);
    public abstract void drawBlockAdd(Point pos);
    public abstract void drawBlockRemove(Point pos);
    public abstract void drawDomeAdd(Point pos);
    public abstract void drawDomeRemove(Point pos);
}
