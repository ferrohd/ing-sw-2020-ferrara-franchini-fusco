package it.polimi.ingsw.PSP14.core;

import it.polimi.ingsw.PSP14.core.Direction;
import it.polimi.ingsw.PSP14.core.InvalidActionException;
import it.polimi.ingsw.PSP14.core.Point;
import it.polimi.ingsw.PSP14.core.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorkerTest {

    private Worker worker;

    @BeforeEach
    void setUp() {
        worker = new Worker(new Point(0,0));
    }

    @Test
    void moveShouldThrowWhenMovingOutsideTheBoard() {
        assertThrows(InvalidActionException.class, () -> {
            worker.move(Direction.N);
            worker.move(Direction.N);
            worker.move(Direction.N);
            worker.move(Direction.N);
            worker.move(Direction.N);
            worker.move(Direction.N);
        });
    }

    @Test
    void getPosShouldReturnZeroZero() {
        assertTrue(worker.getPos().equals(new Point(0, 0)));
    }
}