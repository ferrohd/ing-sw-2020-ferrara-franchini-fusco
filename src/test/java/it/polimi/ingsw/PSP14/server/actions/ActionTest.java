package it.polimi.ingsw.PSP14.server.actions;

import it.polimi.ingsw.PSP14.server.actions.*;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ActionTest {

    class MyAction extends Action {
        private static final long serialVersionUID = 1L;

        public MyAction() {
            super("user");
        }

        public boolean execute(Match match, List<ClientConnection> clients) {return true;}
    }

    private final MyAction myAction = new MyAction();
    private final Instant time = Instant.now();

    @Test
    public void actionShouldReturnTimestamp() {
        assertEquals(time, myAction.getTimestamp());
    }
}