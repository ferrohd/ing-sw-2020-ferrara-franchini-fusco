package it.polimi.ingsw.PSP14.core.net;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.PingMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * A task that handles the incoming messages.
 */
public class TCPIn implements Runnable {
    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    private final ObjectInputStream in;
    private Instant lastTimestamp = Instant.now();
    private final Object timestampLock = new Object();

    public TCPIn(ObjectInputStream in) {
        this.in = in;
    }

    /**
     * Indefinitely reads objects from the socket until an exception occurs.
     * Detects ping messages and discards them, updating the lastTimestamp attribute.
     */
    @Override
    public void run() {
        while(true) {
            try {
                Message message = (Message) in.readObject();
                if(message instanceof PingMessage)
                    synchronized(timestampLock) {
                        lastTimestamp = Instant.now();
                    }
                else
                    queue.add(message);
            } catch(ClassNotFoundException | IOException e) {
                lastTimestamp = Instant.EPOCH;
                return;
            }
        }
    }

    /**
     * Function called to receive a message.
     * It takes a message from the internal queue and returns it.
     * @return the first message in the internal queue
     * @throws IOException if interrupted or too much time has elapsed since the last Ping
     */
    public Message receiveMessage() throws IOException {
        try {
            Message message;
            do {
                synchronized (timestampLock) {
                    if (Duration.between(lastTimestamp, Instant.now()).toMillis() > 5000)
                        throw new IOException("Too much time elapsed!");
                }
                message = queue.poll(100, TimeUnit.MILLISECONDS);
            } while(message == null);
            return message;
        } catch(InterruptedException e) {throw new IOException();}
    }

    /**
     * Closes the connection.
     * @throws IOException if the connection can't be closed.
     */
    public void close() throws IOException {
        in.close();
    }
}
