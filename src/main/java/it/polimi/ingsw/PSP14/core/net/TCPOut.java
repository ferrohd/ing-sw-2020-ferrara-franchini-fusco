package it.polimi.ingsw.PSP14.core.net;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.PingMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * A task that handles the outgoing messages.
 */
public class TCPOut implements Runnable {
    private final ObjectOutputStream out;

    public TCPOut(ObjectOutputStream out) {
        this.out = out;
    }

    /**
     * Indefinitely sends ping messages until an exception occurs.
     */
    @Override
    public void run() {
        while (true) {
            try {
                synchronized (out) {
                    out.writeObject(new PingMessage());
                }
                Thread.sleep(1000);
            } catch (IOException | InterruptedException e) {
                return;
            }
        }
    }

    /**
     * Sends a message.
     *
     * @param message the message to be sent
     * @throws IOException if the message can't be sent
     */
    public void sendMessage(Message message) throws IOException {
        synchronized (out) {
            out.writeObject(message);
        }
    }

    /**
     * Closes the connection.
     *
     * @throws IOException if the connection can't be closed.
     */
    public void close() throws IOException {
        out.close();
    }
}
