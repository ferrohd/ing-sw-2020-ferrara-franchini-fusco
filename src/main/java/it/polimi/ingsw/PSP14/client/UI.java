package it.polimi.ingsw.PSP14.client;

import it.polimi.ingsw.PSP14.core.Cell;
import it.polimi.ingsw.PSP14.core.Player;
import it.polimi.ingsw.PSP14.core.Point;

public abstract class   UI {

    Player[] players;

    public UI(int numberOf_players) {
        players = new Player[numberOf_players];
    }

    /**
     * @param newPlayer new player to be registered in the view
     */
    public void registerPlayer(Player newPlayer) {
        for(int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = newPlayer;
                break;
            }
        }
    }

    /**
     * @param removedPlayer existing player to be removed from the view
     */
    public void removePlayer(Player removedPlayer) {
        for(int i = 0; i < players.length; i++) {
            if (players[i].equals(removedPlayer)) {
                players[i] = null;
                break;
            }
        }
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
