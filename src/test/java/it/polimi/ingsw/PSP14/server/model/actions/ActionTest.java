package it.polimi.ingsw.PSP14.server.model.actions;

import it.polimi.ingsw.PSP14.server.model.MatchModel;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ActionTest {

    class MyAction extends Action {
        private static final long serialVersionUID = 1L;

        public MyAction() {
            super("user");
        }

        @Override
        public void execute(MatchModel model) {
        }
    }

    private final MyAction myAction = new MyAction();

    @Test
    public void actionShouldReturnCorrectUsername() {
        assertEquals("user", myAction.getUser());
    }

    @Test
    public void correctTimestamp() {
        assertTrue(myAction.getTimestamp().isBefore(Instant.now()));
    }
}