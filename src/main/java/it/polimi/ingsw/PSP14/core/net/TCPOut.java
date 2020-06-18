package it.polimi.ingsw.PSP14.core.net;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.PingMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A task that handles the outgoing messages.
 */
public class TCPOut implements Runnable {
    private BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    private final ObjectOutputStream out;

    public TCPOut(ObjectOutputStream out) {
        this.out = out;
    }

    @Override
    public void run() {
        while(true) {
            try {
                synchronized(out) {
                    out.writeObject(new PingMessage());
                }
                Thread.sleep(1000);
            } catch(IOException | InterruptedException e) {
                return;
            }
        }
    }

    public void sendMessage(Message message) throws IOException {
        synchronized (out) {
            out.writeObject(message);
        }
    }

    public void close() throws IOException {
        out.close();
    }
}
