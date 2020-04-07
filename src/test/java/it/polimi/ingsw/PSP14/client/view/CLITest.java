package it.polimi.ingsw.PSP14.client.view;

import it.polimi.ingsw.PSP14.client.InvalidSettingsException;
import it.polimi.ingsw.PSP14.core.model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CLITest {

    @Test
    void generalTest() {
        UI ui;
        try {
            ui = UIFactory.getUI("cli");
        } catch(InvalidSettingsException e) {
            return;
        }
        ui.registerPlayer("ferroHD");
        ui.drawWorkerSet(new Point(0,0), "ferroHD");
        ui.drawWorkerSet(new Point(3,3), "ferroHD");
        ui.update();
    }
}