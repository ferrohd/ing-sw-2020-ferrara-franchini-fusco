package it.polimi.ingsw.PSP14.core;

import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ActionTest {

    static class MyAction extends Action {
    }

    private final MyAction myAction = new MyAction();
    private final Instant time = Instant.now();

    @Test
    public void actionShouldReturnTimestamp() {
        assertEquals(time, myAction.getTimestamp());
    }
}