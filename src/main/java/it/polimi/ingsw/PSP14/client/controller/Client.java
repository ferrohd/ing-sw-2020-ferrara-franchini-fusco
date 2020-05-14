package it.polimi.ingsw.PSP14.client.controller;

import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.client.view.UIFactory;

import java.io.IOException;
import java.net.Socket;

/**
 * This class is the entry point for the client, which handles the instancing of
 * the match, setting the correct UI (CLI or GUI), connection to the server.
 */
public class Client {
    public static void main(String[] args) throws InterruptedException {
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

        ui.welcome();
        String hostname;
        int port;
        hostname = settings.get("hostname");
        port = Integer.parseInt(settings.get("port"));
        ui.notifyConnection(hostname, port);

        Socket serverSocket = null;
        boolean connected = false;
        while (!connected) {
            try {
                serverSocket = new Socket(hostname, port);
                connected = true;
                ui.notify("Connected!");
            } catch (IOException e) {
                ui.notify("Could not connect to the server. Retrying...");
            }
        }
        ServerConnection connection = new TCPServerConnection(serverSocket);

        ClientMatch match = new ClientMatch(connection, ui);
        Thread mainThread = new Thread(match);
        mainThread.start();
        mainThread.join();
    }
}