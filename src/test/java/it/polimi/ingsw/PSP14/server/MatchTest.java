package it.polimi.ingsw.PSP14.server;

import it.polimi.ingsw.PSP14.core.model.PlayerNotFoundException;
import it.polimi.ingsw.PSP14.server.match.MatchModel;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MatchTest {

    @Test
    void matchShouldInstantiate() {
        ArrayList<String> mockUsernames = new ArrayList<>();
        mockUsernames.add("Ada");
        mockUsernames.add("Bob");
        mockUsernames.add("Carl");

        MatchModel model = new MatchModel(new HashSet<>(mockUsernames));

        assertAll("Check if all players are present",
                () -> assertEquals("Ada", model.getPlayerByUsername("Ada").getUsername()),
                () -> assertEquals("Bob", model.getPlayerByUsername("Bob").getUsername()),
                () -> assertEquals("Carl", model.getPlayerByUsername("Carl").getUsername()),
                () -> assertThrows(PlayerNotFoundException.class, () -> model.getPlayerByUsername("Dan").getUsername())
        );
    }

}
