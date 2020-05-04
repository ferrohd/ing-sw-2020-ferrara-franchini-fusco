package it.polimi.ingsw.PSP14.client.view;

import it.polimi.ingsw.PSP14.client.InvalidSettingsException;
import it.polimi.ingsw.PSP14.server.model.Point;
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
        ui.registerPlayer("QUB3X");
        ui.drawWorkerSet(new Point(1,1), "QUB3X");
        ui.drawWorkerSet(new Point(3,2), "QUB3X");
        ui.registerPlayer("Yuzon");
        ui.drawWorkerSet(new Point(4,4), "Yuzon");
        ui.drawWorkerSet(new Point(4,2), "Yuzon");
        ui.update();
    }
}