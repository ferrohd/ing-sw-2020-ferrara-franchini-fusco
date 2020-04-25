package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.actions.ApolloMoveAction;
import it.polimi.ingsw.PSP14.server.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ApolloTest {
    Set<String> players;
    Match match;
    List<MoveAction> moves;
    Player player;
    Player opponent;
    Worker worker;

    @BeforeEach
    void setUp() {
        players = new HashSet<>();
        players.add("Ada");
        players.add("Bob");

        match = new Match(players);
        moves = new ArrayList<>();

        try {
            player = match.getPlayerByUsername("Ada");
            opponent = match.getPlayerByUsername("Bob");
        } catch (PlayerNotFoundException e) {
            fail();
        }
        player.setWorker(0, new Point(0,0));
        player.setWorker(1, new Point(0,1));

        opponent.setWorker(0, new Point(4,4));
        opponent.setWorker(1, new Point(3,3));
        worker = player.getWorker(0);
    }

    @Test
    void addMovesShouldAddASingleMove() {

        Apollo.getInstance().addMoves(moves, player, worker, match);
        assertTrue(moves.get(0).equals(new MoveAction("Ada", new Point(0,0), new Point(0, 1))));
        assertEquals(1, moves.size());
    }

    @Test
    void addMovesShouldNotAddAnyMoves() {
        player.setWorker(0, new Point(0, 4));
        Apollo.getInstance().addMoves(moves, player, worker, match);
        assertEquals(0, moves.size());
    }
}
