package it.polimi.ingsw.PSP14.client.view;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class GUIQueues {
    private static final BlockingQueue<String> reqQueue = new LinkedBlockingDeque<>();
    private static final BlockingQueue<Object> resQueue = new LinkedBlockingDeque<>();

    public static BlockingQueue<String> getReq() {
        return reqQueue;
    }
    public static BlockingQueue<Object> getRes() {
        return resQueue;
    }
}
