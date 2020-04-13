package it.polimi.ingsw.PSP14.client.view;

import it.polimi.ingsw.PSP14.client.InvalidSettingsException;
import it.polimi.ingsw.PSP14.core.Point;
import org.junit.jupiter.api.Test;

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