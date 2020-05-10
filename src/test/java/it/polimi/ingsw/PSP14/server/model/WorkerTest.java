package it.polimi.ingsw.PSP14.server.model;

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
    void getPosShouldWork() {
        assertTrue(worker.getPos().equals(new Point(0, 0)));
    }

    @Test
    void moveShouldWork() {
        worker.move(Direction.N);
        assertTrue(worker.getPos().equals(new Point(0, 1)));
    }

    @Test
    void workerShouldInstantiate() {
        Worker testWorker = new Worker(new Point(2,2));
        assertTrue(testWorker.getPos().equals(new Point(2,2)));
    }

    @Test
    void setPositionShouldWork() {
        worker.setPos(new Point(2,2));
        assertTrue(worker.getPos().equals(new Point(2,2)));
    }
}