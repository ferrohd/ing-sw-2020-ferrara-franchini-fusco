package it.polimi.ingsw.PSP14.core;

import it.polimi.ingsw.PSP14.core.model.actions.*;
import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ActionTest {

    class MyAction extends Action {
        public MyAction() {
            super();
        }
    }

    private final MyAction myAction = new MyAction();
    private final Instant time = Instant.now();

    @Test
    public void actionShouldReturnTimestamp() {
        assertEquals(time, myAction.getTimestamp());
    }
}