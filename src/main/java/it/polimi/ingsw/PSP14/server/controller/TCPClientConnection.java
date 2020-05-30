package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.messages.ChoiceMessage;
import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.PingMessage;
import it.polimi.ingsw.PSP14.core.messages.StringMessage;
import it.polimi.ingsw.PSP14.core.net.TCPIn;
import it.polimi.ingsw.PSP14.core.net.TCPOut;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ClientConnection implemented using TCP sockets.
 */
public class TCPClientConnection extends ClientConnection {
    private TCPOut clientOutput;
    private TCPIn clientInput;

    public TCPClientConnection(final Socket socket) throws IOException {
        clientOutput = new TCPOut(new ObjectOutputStream(socket.getOutputStream()));
        clientInput = new TCPIn(new ObjectInputStream(socket.getInputStream()));
        Thread inputThread = new Thread(clientInput);
        inputThread.setName("TCPIn");
        Thread outputThread = new Thread(clientOutput);
        outputThread.setName("TCPOut");
        inputThread.start();
        outputThread.start();
    }

    @Override
    public void sendMessage(Message message) throws IOException {
        clientOutput.sendMessage(message);
    }

    @Override
    public Message receiveMessage() throws IOException {
        return clientInput.receiveMessage();
    }

    @Override
    public int receiveChoice() throws IOException {
        ChoiceMessage choice = (ChoiceMessage) receiveMessage();

        return choice.getIndex();
    }

    @Override
    public String receiveString() throws IOException {
        StringMessage string = (StringMessage) receiveMessage();

        return string.getString();
    }

    @Override
    public void close() throws IOException {
        clientOutput.close();
        clientInput.close();
    }
}