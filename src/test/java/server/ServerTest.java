package server;


import it.polimi.ingsw.ing_sw_2020_ferrara_franchini_fusco.server.Server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ServerTest {

    @Test
    public void greetingsShouldReturnName() {
        Server tester = new Server();

        assertEquals("Hi Mark", Server.greetings("Mark"));
    }
}