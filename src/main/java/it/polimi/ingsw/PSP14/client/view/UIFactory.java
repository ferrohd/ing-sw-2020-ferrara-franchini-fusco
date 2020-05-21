package it.polimi.ingsw.PSP14.client.view;

import it.polimi.ingsw.PSP14.client.controller.InvalidSettingsException;
import it.polimi.ingsw.PSP14.client.view.cli.CLI;
import it.polimi.ingsw.PSP14.client.view.gui.GUI;
import it.polimi.ingsw.PSP14.client.controller.InvalidSettingsException;

public class UIFactory {
    private UIFactory() {}

    public static UI getUI(String setting) throws InvalidSettingsException {
        switch(setting) {
            case "cli":
                return new CLI();
            case "gui":
                return new GUI();
            default:
                throw new InvalidSettingsException();
        }
    }
}
