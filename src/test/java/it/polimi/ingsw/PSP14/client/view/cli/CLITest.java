package it.polimi.ingsw.PSP14.client.view.cli;

import it.polimi.ingsw.PSP14.client.controller.InvalidSettingsException;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.client.view.UIFactory;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CLITest {
    UI ui;

    @BeforeEach
    void setup() {
        try {
            ui = UIFactory.getUI("cli");
            ui.registerPlayer("ferroHD");
            ui.setWorker(new Point(0,0), 0,"ferroHD");
            ui.setWorker(new Point(3,3), 1,"ferroHD");
            ui.registerPlayer("QUB3X");
            ui.setWorker(new Point(1,1), 0, "QUB3X");
            ui.setWorker(new Point(3,2), 1, "QUB3X");
            ui.registerPlayer("Yuzon");
            ui.setWorker(new Point(4,4), 0,"Yuzon");
            ui.setWorker(new Point(4,2), 1,"Yuzon");
            ui.incrementCell(new Point(0,0));
            ui.incrementCell(new Point(0,0));
            ui.incrementCell(new Point(0,0));
            ui.incrementCell(new Point(0,1));
            ui.incrementCell(new Point(1,0));
            ui.setDome(new Point(1, 1));
            ui.setWorker(new Point(2,0),0, "ferroHD");
            // DEBUG
            ((CLI) ui).debug_setPlayerUsername("ferroHD");
        } catch (InvalidSettingsException ignored) {
        }
//        Doesn't work
//        InputStream fakeIn = new ByteArrayInputStream("1 1".getBytes());
//        System.setIn(fakeIn);
    }

    @Test
    void generalTest() {
        // THIS SHOULD DRAW A BOARD
        ui.update();
    }

    @Test
    void welcome() {
        assertDoesNotThrow(() -> ui.welcome());
    }

    @Test
    void getLobbySize() {
        assertDoesNotThrow(() -> ui.getLobbySize());
    }

    @Test
    void testNotify() {
        ui.showNotification("This is a short message!");
    }

    // This will overflow
    @Test
    void testNotifySuperLong() {
        ui.showNotification("Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi" +
                "ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit " +
                "in voluptate velit esse cillum dolore eu fugiat nulla pariatur.");
    }

    @Test
    void askUsername() {
        assertDoesNotThrow(() -> ui.askUsername());
    }

    @Test
    void chooseFirstPlayer() {
        List<PlayerProposal> _list = new ArrayList<>();
        _list.add(new PlayerProposal("ferroHD"));
        _list.add(new PlayerProposal("QUB3X"));
        _list.add(new PlayerProposal("Yuzon"));

        ui.chooseFirstPlayer(_list);
    }

    @Test
    void chooseGod() {
        List<GodProposal> _list = new ArrayList<>();
        _list.add(new GodProposal("Apollo"));
        _list.add(new GodProposal("Athena"));
        _list.add(new GodProposal("Minotaur"));

        try {
            ui.chooseGod(_list);
        }catch(InterruptedException e) {}
    }

    @Test
    void chooseAvailableGods() throws InterruptedException {
        List<GodProposal> _list = new ArrayList<>();
        _list.add(new GodProposal("Apollo"));
        _list.add(new GodProposal("Artemis"));
        _list.add(new GodProposal("Athena"));
        _list.add(new GodProposal("Chronus"));
        _list.add(new GodProposal("Demeter"));
        _list.add(new GodProposal("Hephaestus"));
        _list.add(new GodProposal("Hestia"));
        _list.add(new GodProposal("Minotaur"));
        _list.add(new GodProposal("Pan"));
        _list.add(new GodProposal("Poseidon"));
        _list.add(new GodProposal("Prometheus"));
        _list.add(new GodProposal("Triton"));
        _list.add(new GodProposal("Zeus"));

        try {

            ui.chooseAvailableGods(_list);
        } catch(InterruptedException e) {}
    }

    @Test
    void chooseAvailableFewGods() throws InterruptedException {
        List<GodProposal> _list = new ArrayList<>();
        _list.add(new GodProposal("Apollo"));
        _list.add(new GodProposal("Artemis"));
        _list.add(new GodProposal("Athena"));
        _list.add(new GodProposal("Chronus"));
        _list.add(new GodProposal("Demeter"));
        try{
            ui.chooseAvailableGods(_list);
        } catch(InterruptedException e) {}

}

    @Test
    void chooseWorker() throws InterruptedException {
        System.out.println();
        ui.chooseWorker(new ArrayList<>(Arrays.asList(0, 1)));
    }

    @Test
    void chooseWorkerInitialPosition() {
        ui.chooseWorkerInitialPosition();
    }

    @Test
    void chooseMoves() throws InterruptedException {
        List<MoveProposal> _l = new ArrayList<>();
        _l.add(new MoveProposal(new Point(0,0)));
        _l.add(new MoveProposal(new Point(1,1)));
        _l.add(new MoveProposal(new Point(0,1)));
        _l.add(new MoveProposal(new Point(0,0)));
        _l.add(new MoveProposal(new Point(1,1)));
        _l.add(new MoveProposal(new Point(0,1)));
        _l.add(new MoveProposal(new Point(0,0)));
        _l.add(new MoveProposal(new Point(1,1)));
        _l.add(new MoveProposal(new Point(0,1)));
        ui.chooseMove(_l);
    }

    @Test
    void chooseYesNo() {
        ui.chooseYesNo("Will you marry me?");
    }
}