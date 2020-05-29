package it.polimi.ingsw.PSP14.core.net;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.PingMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TCPIn implements Runnable {
    private BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    private ObjectInputStream in;
    private Instant lastTimestamp = Instant.now();

    public TCPIn(ObjectInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Message message = (Message) in.readObject();
                if(message instanceof PingMessage)
                    lastTimestamp = ((PingMessage)message).getTimestamp();
                else
                    queue.add(message);
            } catch(ClassNotFoundException | IOException e) {
                lastTimestamp = Instant.EPOCH;
            }
        }
    }

    public Message receiveMessage() throws IOException {
        try {
            if(Duration.between(lastTimestamp, Instant.now()).toMillis() > 5000) throw new IOException();
            return queue.take();
        } catch(InterruptedException e) {throw new IOException();}
    }

    public void close() throws IOException {
        in.close();
    }
}
