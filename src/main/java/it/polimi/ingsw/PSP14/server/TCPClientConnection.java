package it.polimi.ingsw.PSP14.server;

import it.polimi.ingsw.PSP14.core.model.actions.Action;

import java.net.Socket;

/**
 * ClientConnection implemented using TCP sockets.
 */
public class TCPClientConnection implements ClientConnection {
    private final Socket clientSocket;

    public TCPClientConnection(Socket socket) {
        clientSocket = socket;
    }

    public int requestGameOptions() {
        return 3;
    }

    public void sendFatalError() {
        //TODO
    }

    @Override
    public String getPlayerUsername() {
        //TODO: Implement function
        // Use a cache, as username should not change
        return "";
    }

    @Override
    public void sendAction(Action action) {
        //TODO
    }

    @Override
    public Action receiveAction() {
        //TODO
        return null;
    }
}
