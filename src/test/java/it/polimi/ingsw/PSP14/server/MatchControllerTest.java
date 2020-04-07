/*
package it.polimi.ingsw.PSP14.server;

import it.polimi.ingsw.PSP14.core.controller.gods.GodController;
import it.polimi.ingsw.PSP14.core.model.PlayerNotFoundException;
import it.polimi.ingsw.PSP14.core.model.actions.Action;
import it.polimi.ingsw.PSP14.core.model.actions.PickGodAction;
import it.polimi.ingsw.PSP14.server.match.MatchController;
import it.polimi.ingsw.PSP14.server.match.MatchModel;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class MatchControllerTest {

    // List of PlayerUsername
    private List<String> players;
    // List of Gods
    private List<String> selectedGods;
    // PlayerUsername <-> ClientConnection
    private Map<String, ClientConnection> clients;
    // PlayerUsername <--> GodController
    private Map<String, GodController> gods;
    // Contains data about players, board...
    private MatchModel match;

    @BeforeEach
    void initData() {
        players = new ArrayList<>();
        players.add("Ada");
        players.add("Bob");
        players.add("Carl");

        clients = new HashMap<>();

        gods = new HashMap<>();
        selectedGods = new ArrayList<>();

        match = new MatchModel(new HashSet<>(players));
    }

    @AfterEach
    void freeData() {
        players = null;
        clients = null;
        selectedGods = null;
        gods = null;
        match = null;
    }

    @Test
    void matchShouldInstantiate() {
        assertAll("Check if all players are present",
                () -> assertEquals("Ada", match.getPlayerByUsername("Ada").getUsername()),
                () -> assertEquals("Bob", match.getPlayerByUsername("Bob").getUsername()),
                () -> assertEquals("Carl", match.getPlayerByUsername("Carl").getUsername()),
                () -> assertThrows(PlayerNotFoundException.class, () -> match.getPlayerByUsername("Dan"))
        );
    }

    @Test
    void run() {

    }

    @Test
    void firstPlayerShouldSelectGodsCorrectly() {
        // Init Gods
        List<String> availableGods = new ArrayList<>();
        availableGods.add("Apollo");
        availableGods.add("Minerva");
        availableGods.add("Zeus");
        availableGods.add("Pan");

        // Test stuff
        List<String> expected = new ArrayList<>();
        expected.add("Apollo");
        expected.add("Minerva");
        expected.add("Pan");

        List<String> res = MatchController.firstPlayerSelectsGods(
                availableGods,
                players,
                clients
        );

        assertEquals(expected, res);
    }

    @Test
    void playersPickOwnGod() {
    }
}

// MOCK
class MockClientConnection implements ClientConnection {
    String msg;

    MockClientConnection(String msg) {
        this.msg = msg;
    }

    public Action receiveAction() {
        return new PickGodAction(msg);
    }

    @Override
    public int requestGameOptions() {
        return 0;
    }

    @Override
    public void sendFatalError() {

    }

    @Override
    public String getPlayerUsername() {
        return null;
    }

    @Override
    public void sendAction(Action action) {

    }
}
*/