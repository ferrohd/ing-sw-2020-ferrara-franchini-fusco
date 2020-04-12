package it.polimi.ingsw.PSP14.core;

/*
public class BoardTest {

    private Board board = new Board();

    @Test
    void getTowerSizeShouldReturnZero() {
        assertEquals(0, board.getTowerSize(0,0));
    }

    @Test
    void getTowerSizeShouldThrowIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            board.getTowerSize(6, 6);
        });
    }

    @Test
    void incrementTowerSizeShouldReturnThree() {
        try {
            board.incrementTowerSize(1, 2);
            board.incrementTowerSize(1, 2);
            board.incrementTowerSize(1, 2);
        } catch (TowerSizeException e) {
            e.printStackTrace();
        }
        assertEquals(3, board.getTowerSize(1,2));
    }

    @Test
    void incrementTowerSizeShouldThrowTowerSizeException() {
        assertThrows(TowerSizeException.class, () -> {
            board.incrementTowerSize(1, 2);
            board.incrementTowerSize(1, 2);
            board.incrementTowerSize(1, 2);
            board.incrementTowerSize(1, 2);
        });
    }

    @Test
    void incrementTowerSizeShouldThrowIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            board.incrementTowerSize(6, 6);
        });
    }

    @Test
    void setAsCompletedShouldCompleteTower() {
        board.setAsCompleted(3, 3);
        assertTrue(board.getIsCompleted(3, 3));
    }

    @Test
    void setAsCompletedShouldThrowIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            board.setAsCompleted(6, 6);
        });
    }

    @Test
    void getIsCompletedShouldNotReturnTrueOnUncompletedTower() {
        assertFalse(board.getIsCompleted(0,0));
    }

    @Test
    void getIsCompletedShouldThrowIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            board.getIsCompleted(6, 6);
        });
    }
}
*/