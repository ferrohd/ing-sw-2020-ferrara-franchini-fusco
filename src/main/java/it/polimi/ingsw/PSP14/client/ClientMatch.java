package it.polimi.ingsw.PSP14.client;

import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.messages.ClientExecutableMessage;
import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;

import java.io.IOException;
import java.net.Socket;

public class ClientMatch implements Runnable {
    private ServerConnection serverConnection;
    private UI ui;

    public ClientMatch(ServerConnection serverConnection, UI ui) {
        this.serverConnection = serverConnection;
        this.ui = ui;
    }

    @Override
    public void run() {
        while(true) {
            ClientExecutableMessage inMessage;
            try {
                inMessage = (ClientExecutableMessage) serverConnection.receiveMessage();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
                return;
            }

            inMessage.execute(ui, serverConnection);
        }
    }
}
