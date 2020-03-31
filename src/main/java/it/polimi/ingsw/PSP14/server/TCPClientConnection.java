package it.polimi.ingsw.PSP14.server;

import java.net.Socket;

public class TCPClientConnection implements ClientConnection {
    private final Socket clientSocket;

    public TCPClientConnection(Socket socket) {
        clientSocket = socket;
    }

    public int requestGameOptions() {
        return 3;
    }

    public void sendFatalError() {

    }
}
