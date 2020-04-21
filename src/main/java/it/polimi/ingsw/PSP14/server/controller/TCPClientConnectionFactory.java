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
    private final BlockingQueue<TCPClientConnection> clientQueue = new LinkedBlockingQueue<>();

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
            TCPClientConnection newConnection = new TCPClientConnection(newConnectionSocket);
            try {
                clientQueue.put(newConnection);
            } catch(InterruptedException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public ClientConnection getClientConnection() throws InterruptedException {
        return clientQueue.take();
    }
}
