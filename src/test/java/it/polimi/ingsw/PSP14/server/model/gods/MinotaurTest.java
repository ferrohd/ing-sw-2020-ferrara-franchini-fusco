package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinotaurTest {
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


        player = match.getPlayerByUsername("Ada");
        opponent = match.getPlayerByUsername("Bob");
        player.setWorker(0, new Point(0,0));
        player.setWorker(1, new Point(4,4));

        opponent.setWorker(0, new Point(0,1));
        opponent.setWorker(1, new Point(4,3));
        worker = player.getWorker(0);
    }


    @Test
    void addMovesShouldAddASingleMove() {

        new Minotaur("Ada").addMoves(moves, player, worker, match);
        assertTrue(moves.get(0).equals(new MoveAction("Ada", new Point(0,0), new Point(0, 1))));
        System.out.println(moves.get(0).toString());
        assertEquals(1, moves.size());
    }

    @Test
    void addMovesShouldNotAddAnyMoves() {
        // Player worker has not opponent workers close to it
        player.setWorker(0, new Point(0, 4));
        new Minotaur("Ada").addMoves(moves, player, worker, match);
        assertEquals(0, moves.size());
    }

    @Test
    void playerShouldNotPushOpponentOutsideTheBoard() {
        // Player worker cannot push opponent outside of boundaries
        // It passes, but it shouldn't?
        opponent.setWorker(0, new Point(0, 0));
        player.setWorker(0, new Point(1,0));
        new Minotaur("Ada").addMoves(moves, player, worker, match);
        assertEquals(0, moves.size());
    }
}
