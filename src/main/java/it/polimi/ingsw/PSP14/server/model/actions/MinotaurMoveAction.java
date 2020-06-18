package it.polimi.ingsw.PSP14.server.model.actions;

import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;

/**
 * The implementation of a custom move of Minotaur.
 */
public class MinotaurMoveAction extends MoveAction {
    private Point opponentNewPos;
    private int opponentWorkerIndex;
    private String opponent;

    public MinotaurMoveAction(String user, Point from, Point to) {
        super(user, from, to);
    }

    @Override
    public void execute(MatchModel model) throws IOException {
        for(Player p: model.getPlayerMap()) {
            for(int i = 0; i < 2; ++i) {
                if(p.getWorkerPos(i).equals(getTo()) &&
                        !p.getUsername().equals(getUser())) {
                    super.execute(model);
                    Point newPos = new Point(
                            2*getTo().getX() - getFrom().getX(),
                            2*getTo().getY() - getFrom().getY()
                    );
                    opponentNewPos = newPos;
                    opponentWorkerIndex = i;
                    opponent = p.getUsername();
                    p.setWorker(i, newPos);
                }
            }
        }
    }
}
