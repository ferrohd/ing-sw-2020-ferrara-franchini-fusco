package it.polimi.ingsw.PSP14.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.polimi.ingsw.PSP14.core.Board;
import it.polimi.ingsw.PSP14.core.Point;
import it.polimi.ingsw.PSP14.core.TowerSizeException;

public class BoardTest {

    private Board board = new Board();


    @BeforeEach
    void reset() {
        board = new Board();
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
        } catch (TowerSizeException e) {
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
    void setAsCompletedShouldCompleteTower() {
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

    @Test
    void getCellShouldThrow() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            board.getCell(new Point(6, 6));
        });
    }
}
