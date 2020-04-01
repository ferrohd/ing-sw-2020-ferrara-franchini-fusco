package it.polimi.ingsw.PSP14.client;

import it.polimi.ingsw.PSP14.core.Cell;
import it.polimi.ingsw.PSP14.core.Player;

public abstract class UI {

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
    public abstract void drawWorkerAdd();
    public abstract void drawWorkerRemove();
    public abstract void drawWorkerMove();
    public abstract void drawBlockAdd();
    public abstract void drawBlockRemove();
    public abstract void drawDomeAdd();
    public abstract void drawDomeRemove();
}
