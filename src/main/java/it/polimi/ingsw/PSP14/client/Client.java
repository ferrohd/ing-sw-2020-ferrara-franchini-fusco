package it.polimi.ingsw.PSP14.client;

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
        ClientInterface ci;
        try {
            ci = ClientInterfaceFactory.getClientInterface(settings.get("interface"));
        } catch (InvalidSettingsException e) {
            System.out.println(settings.get("interface") + " is not a valid interface");
            e.printStackTrace();
            return;
        }

        ci.welcome();
    }
}