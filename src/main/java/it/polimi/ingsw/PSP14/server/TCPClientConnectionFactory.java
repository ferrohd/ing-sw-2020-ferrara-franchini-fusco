package it.polimi.ingsw.PSP14.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TCPClientConnectionFactory implements ClientConnectionFactory {
    private final ServerSocket serverSocket;
    private final BlockingQueue<TCPClientConnection> clientQueue = new LinkedBlockingQueue<>();

    public TCPClientConnectionFactory(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void run() {
        while(true) {
            Socket newConnectionSocket;
            try {
                newConnectionSocket = serverSocket.accept();
            } catch(IOException e) {
                continue;
            }
            TCPClientConnection newConnection = new TCPClientConnection(newConnectionSocket);
            synchronized(clientQueue) {
                clientQueue.add(newConnection);
            }
        }
    }

    public ClientConnection getClientConnection() throws InterruptedException {
        TCPClientConnection toReturn;
        synchronized(clientQueue) {
            toReturn = clientQueue.take();
        }
        return toReturn;
    }
}
