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

    /**
     * Main method of the class.
     * it continuously checks for incoming connection requests. If a connection succeeds it wraps
     * the client socket in a TCPClientConnection object and puts it in an internal queue to be
     * retrieved later with getClientConnection.
     */
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

    @Override
    public ClientConnection getClientConnection() throws InterruptedException {
        while (true) {
            try {
                ClientConnection toReturn = clientQueue.take();
                toReturn.ping();
                return toReturn;
            } catch (IOException e)  {
                System.out.println("Discarded bad connection!");
            }
        }
    }

    @Override
    public void addClientConnection(ClientConnection clientConnection) {
        clientQueue.add(clientConnection);
    }
}
