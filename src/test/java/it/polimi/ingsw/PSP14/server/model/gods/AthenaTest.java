package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.messages.ChoiceMessage;
import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.StringMessage;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.FakeClientConnection;
import it.polimi.ingsw.PSP14.server.model.FakeMatch;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.actions.Action;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.board.Board;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AthenaTest {
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        Match match = new FakeMatch() {
            @Override
            public Action getLastAction() {
                return new MoveAction("athenaOwner", new Point(0, 0), new Point(1, 1));
            }
        };
        ClientConnection client = new FakeClientConnection();
        God athena = new Athena("athenaOwner");
        Board board = match.getBoard();
        board.incrementTowerSize(new Point(1, 1));
        board.incrementTowerSize(new Point(3, 3));

        athena.beforeTurn("athenaOwner", client, match);
        athena.afterMove("athenaOwner", 0, client, match);

        List<MoveAction> moves = new ArrayList<>();
        moves.add(new MoveAction("opponent", new Point(2, 2), new Point(3, 3)));

        athena.removeMoves(moves, "opponent", 0, match);

        assertEquals(moves.size(), 0);
    }
}
