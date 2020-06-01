package it.polimi.ingsw.PSP14.server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ClientConnectionFactory implemented using TCP sockets.
 */
public class TCPClientConnectionFactory implements ClientConnectionFactory {
    private final ServerSocket serverSocket;
    private final BlockingQueue<ClientConnection> clientQueue = new LinkedBlockingQueue<>();

    public TCPClientConnectionFactory(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void run() {
        while(true) {
            Socket newConnectionSocket;
            try {
                newConnectionSocket = serverSocket.accept();
                System.out.println("New client connected!");
            } catch(IOException e) {
                continue;
            }
            try {
                ClientConnection newConnection = new TCPClientConnection(newConnectionSocket);
                clientQueue.put(newConnection);
            } catch(InterruptedException | IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public ClientConnection getClientConnection() throws InterruptedException {
        return clientQueue.take();
    }

    @Override
    public void addClientConnection(ClientConnection clientConnection) {
        clientQueue.add(clientConnection);
    }
}
