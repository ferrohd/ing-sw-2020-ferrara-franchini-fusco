package it.polimi.ingsw.PSP14.client;

import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.client.view.UIFactory;

import java.io.IOException;

public class Client {
    public static void main(String[] args) {
        // getting settings
        SettingsParser settings;
        try {
            settings = new SettingsParser("src/main/resources/settings.set");
        } catch(IOException e) {
            System.out.println("Could not read settings file:");
            e.printStackTrace();
            return;
        }

        // starting user interface
        UI ui;
        try {
            ui = UIFactory.getUI(settings.get("interface"));
        } catch (InvalidSettingsException e) {
            System.out.println(settings.get("interface") + " is not a valid interface");
            e.printStackTrace();
            return;
        }

        //ui.welcome();
    }
}