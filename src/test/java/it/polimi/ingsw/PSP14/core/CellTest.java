package it.polimi.ingsw.PSP14.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    private Cell cell;

    @BeforeEach
    void setUp() {
        cell = new Cell();
    }

    @Test
    void getTowerSizeShouldReturnZero() {
        assertEquals(0, cell.getTowerSize());
    }

    @Test
    void getTowerSizeShouldNotReturnMoreThanThree() {
        assertThrows(TowerSizeException.class, () -> {
            cell.incrementTowerSize();
            cell.incrementTowerSize();
            cell.incrementTowerSize();
            cell.incrementTowerSize();
        });
        assertTrue(cell.getTowerSize() <= 3);
    }

    @Test
    void getIsCompletedShouldReturnFalse() {
        assertFalse(cell.getIsCompleted());
    }

    @Test
    void getIsCompletedShouldReturnTrue() {
        cell.setAsCompleted();
        assertTrue(cell.getIsCompleted());
    }

    @Test
    void incrementTowerSizeShouldNotThrow() {
        assertDoesNotThrow(() -> {
            cell.incrementTowerSize();
        });
        assertEquals(1, cell.getTowerSize());
    }

    @Test
    void incrementTowerSizeShouldThrowTowerSizeException() {
        assertThrows(TowerSizeException.class, () -> {
            cell.incrementTowerSize();
            cell.incrementTowerSize();
            cell.incrementTowerSize();
            cell.incrementTowerSize();
        });
        assertEquals(3, cell.getTowerSize());
    }

    @Test
    public void setAsCompletedShouldCompleteTower() {
        cell.setAsCompleted();
        assertTrue(cell.getIsCompleted());
    }
}