package it.polimi.ingsw.PSP14.client;

import it.polimi.ingsw.PSP14.client.controller.*;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.client.view.UIFactory;

import java.io.IOException;
import java.net.Socket;

/**
 * This class is the entry point for the client, which handles the instancing of
 * the match, setting the correct UI (CLI or GUI), connection to the server.
 */
public class Client {

    private static final int DEFAULT_PORT = 42069;
    private static final String DEFAULT_ADDRESS = "127.0.0.1";

    /**
     * 0 = CLI, 1 = GUI
     */
    private static String clientMode = "gui";
    private static int port = DEFAULT_PORT;
    private static String address = DEFAULT_ADDRESS;
    private static String settingsLocation = "settings.txt";

    public static void main(String[] args) throws InterruptedException {
        // Parse command line args
        parseArgs(args);

        // starting user interface
        UI ui;
        try {
            ui = UIFactory.getUI(clientMode);
        } catch (InvalidSettingsException e) {
            System.out.println(clientMode + " is not a valid interface");
            e.printStackTrace();
            return;
        }

        // Start the UI and display a welcome message.
        ui.welcome();

        ui.showNotification("Connecting to " + address +":" + port + "...");

        Socket serverSocket = null;
        boolean connected = false;
        while (!connected) {
            try {
                serverSocket = new Socket(address, port);
                connected = true;
                ui.showNotification("Connected! Waiting for a game...");
            } catch (IOException e) {
                ui.showNotification("Could not connect to the server. Retrying...");
            }
        }

        ServerConnection connection;
        try {
            connection = new TCPServerConnection(serverSocket);
        } catch(IOException e) {
            ui.showNotification("Error connecting to the server.");
            return;
        }

        ClientMatch match = new ClientMatch(connection, ui);
        Thread mainThread = new Thread(match);
        mainThread.start();
        //mainThread.join();
    }

    /**
     * By default, the client tries to read a file called settings.txt in the same folder of the jar file.
     * There are additional flags documented below. Flags override the default value/the one specified in settings.txt: for example, if you have specified the interface as GUI and launch the jar with the —cli flag, it will start as CLI.
     *
     *     --gui # Starts the GUI
     *     --cli # Starts the CLI
     *     --address <address> # Specify the server address (IP or URL)
     *     --port <port> # Speficy the server port (a number)
     *     --config <path to config> # Specify a custom path to a config file.
     *
     * A possible config file is the following:
     *
     *     interface: gui
     *     hostname: 127.0.0.1
     *     port: 42069
     * @param args an array of arguments, usually the one provided by the  main method.
     */
    private static void parseArgs(String[] args) {
        boolean overrideMode = false;
        boolean overridePort = false;
        boolean overrideAddr = false;

        // Parse cli settings
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "--cli":
                    clientMode = "cli";
                    overrideMode = true;
                    break;
                case "--gui":
                    clientMode = "gui";
                    overrideMode = true;
                    break;
                case "--port":
                    overridePort = true;
                    if (i < args.length - 1) {
                        try {
                            port = Integer.valueOf(args[++i]);
                        } catch (NumberFormatException e) {
                            port = DEFAULT_PORT;
                        }
                    }
                    break;
                case "--address":
                    overrideAddr = true;
                    if (i < args.length - 1) {
                        address = args[++i];
                    }
                case "--config":
                    if (i < args.length - 1) {
                        settingsLocation = args[++i];
                    }
            }
        }

        // Parse settings file
        // getting settings
        SettingsParser settings;
        try {
            settings = new SettingsParser(settingsLocation);
        } catch(IOException e) {
            System.out.println("Could not find settings file:\n\tsettings.txt\n\nUsing default settings...");
            // e.printStackTrace();
            settings = null;
        }

        // Apply correct parameters
        if (settings != null) {
            if (!overrideMode)
                clientMode = settings.get("interface");
            if (!overrideAddr)
                address = settings.get("hostname");
            if (!overridePort)
                try {
                    port = Integer.valueOf(settings.get("port"));
                } catch (NumberFormatException e) {
                    port = DEFAULT_PORT;
                }
        }
    }
}