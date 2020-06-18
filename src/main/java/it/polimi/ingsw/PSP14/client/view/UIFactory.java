package it.polimi.ingsw.PSP14.client.view;

import it.polimi.ingsw.PSP14.client.controller.InvalidSettingsException;
import it.polimi.ingsw.PSP14.client.view.cli.CLI;
import it.polimi.ingsw.PSP14.client.view.gui.GUI;

/**
 * A class containing static methods to obtain the requested implementation of the UI (CLI or GUI)
 */
public class UIFactory {
    private UIFactory() {}

    /**
     * Launch GUI or CLI based on settings
     * @param setting param used to launch cli or gui
     * @return UI to use
     * @throws InvalidSettingsException if the specified setting isn't implemented
     */
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
