package it.polimi.ingsw.PSP14.client.view;

import it.polimi.ingsw.PSP14.client.controller.InvalidSettingsException;
import it.polimi.ingsw.PSP14.client.model.UIPoint;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CLITest {
    UI ui;

    @BeforeEach
    void setup() {
        try {
            ui = UIFactory.getUI("cli");
            ui.registerPlayer("ferroHD");
            ui.setWorker(new UIPoint(0,0), 0,"ferroHD");
            ui.setWorker(new UIPoint(3,3), 1,"ferroHD");
            ui.registerPlayer("QUB3X");
            ui.setWorker(new UIPoint(1,1), 0, "QUB3X");
            ui.setWorker(new UIPoint(3,2), 1, "QUB3X");
            ui.registerPlayer("Yuzon");
            ui.setWorker(new UIPoint(4,4), 0,"Yuzon");
            ui.setWorker(new UIPoint(4,2), 1,"Yuzon");
            ui.incrementCell(new UIPoint(0,0));
            ui.incrementCell(new UIPoint(0,0));
            ui.incrementCell(new UIPoint(0,0));
            ui.incrementCell(new UIPoint(0,1));
            ui.incrementCell(new UIPoint(1,0));
            ui.setDome(new UIPoint(1, 1));
            ui.setWorker(new UIPoint(2,0),0, "ferroHD");
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
        ui.welcome();
    }

    @Test
    void getLobbySize() {
        ui.getLobbySize();
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
        ui.askUsername();
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

        ui.chooseGod(_list);
    }

    @Test
    void chooseAvailableGods() {
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

        ui.chooseAvailableGods(_list);
    }

    @Test
    void chooseAvailableFewGods() {
        List<GodProposal> _list = new ArrayList<>();
        _list.add(new GodProposal("Apollo"));
        _list.add(new GodProposal("Artemis"));
        _list.add(new GodProposal("Athena"));
        _list.add(new GodProposal("Chronus"));
        _list.add(new GodProposal("Demeter"));

        ui.chooseAvailableGods(_list);
    }

    @Test
    void chooseWorker() {
        System.out.println();
        ui.chooseWorker(new ArrayList<>(Arrays.asList(0, 1)));
    }

    @Test
    void chooseWorkerInitialPosition() {
        ui.chooseWorkerInitialPosition();
    }

    @Test
    void chooseMoves() {
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