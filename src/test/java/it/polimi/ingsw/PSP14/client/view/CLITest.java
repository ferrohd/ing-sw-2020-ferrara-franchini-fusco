package it.polimi.ingsw.PSP14.client.view;

import it.polimi.ingsw.PSP14.client.InvalidSettingsException;
import it.polimi.ingsw.PSP14.client.model.UIPoint;
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
        ui.setWorker(new UIPoint(0,0), 0,"ferroHD");
        ui.setWorker(new UIPoint(3,3), 1,"ferroHD");
        ui.registerPlayer("QUB3X");
        ui.setWorker(new UIPoint(1,1), 0, "QUB3X");
        ui.setWorker(new UIPoint(3,2), 1, "QUB3X");
        ui.registerPlayer("Yuzon");
        ui.setWorker(new UIPoint(4,4), 0,"Yuzon");
        ui.setWorker(new UIPoint(4,2), 1,"Yuzon");
        ui.update();
    }
}