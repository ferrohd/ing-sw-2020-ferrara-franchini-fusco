package it.polimi.ingsw.PSP14.client;

import java.io.IOException;
import java.net.Socket;

public class TCPServerConnection implements ServerConnection {
    private Socket socket;

    public TCPServerConnection(String hostname, int port) throws IOException {
        socket = new Socket(hostname, port);
    }
}
