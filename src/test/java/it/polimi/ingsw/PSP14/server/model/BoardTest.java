package it.polimi.ingsw.PSP14.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.polimi.ingsw.PSP14.server.model.board.Board;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;

import java.io.IOException;
import java.util.ArrayList;

public class BoardTest {

    private Board board = new Board(new ArrayList<>());


    @BeforeEach
    void reset() {
        board = new Board(new ArrayList<>());
    }

    @Test
    void getTowerSizeShouldReturnZero() {
        assertEquals(0, board.getTowerSize(new Point(0, 0)));
    }

    @Test
    void getTowerSizeShouldThrowIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            board.getTowerSize(new Point(6, 6));
        });
    }

    @Test
    void incrementTowerSizeShouldReturnThree() {
        try {
            board.incrementTowerSize(new Point(1, 2));
            board.incrementTowerSize(new Point(1, 2));
            board.incrementTowerSize(new Point(1, 2));
        } catch (TowerSizeException | IOException e) {
            e.printStackTrace();
        }
        assertEquals(3, board.getTowerSize(new Point(1, 2)));
    }

    @Test
    void incrementTowerSizeShouldThrowTowerSizeException() {
        assertThrows(TowerSizeException.class, () -> {
            board.incrementTowerSize(new Point(1, 2));
            board.incrementTowerSize(new Point(1, 2));
            board.incrementTowerSize(new Point(1, 2));
            board.incrementTowerSize(new Point(1, 2));
        });
    }

    @Test
    void incrementTowerSizeShouldThrowIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            board.incrementTowerSize(new Point(6, 6));
        });
    }

    @Test
    void setAsCompletedShouldCompleteTower() throws IOException {
        board.setAsCompleted(new Point(3, 3));
        assertTrue(board.getIsCompleted(new Point(3, 3)));
    }

    @Test
    void setAsCompletedShouldThrowIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            board.setAsCompleted(new Point(6, 6));
        });
    }

    @Test
    void getIsCompletedShouldNotReturnTrueOnUncompletedTower() {
        assertFalse(board.getIsCompleted(new Point(0, 0)));
    }

    @Test
    void getIsCompletedShouldThrowIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            board.getIsCompleted(new Point(6, 6));
        });
    }
}
