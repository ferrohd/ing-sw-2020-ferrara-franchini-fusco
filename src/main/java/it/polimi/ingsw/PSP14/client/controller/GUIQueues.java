package it.polimi.ingsw.PSP14.client.controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class GUIQueues {
    private static final BlockingQueue<Object> reqQueue = new LinkedBlockingDeque<>();
    private static final BlockingQueue<Object> resQueue = new LinkedBlockingDeque<>();

    public static BlockingQueue<Object> getReq() {
        return reqQueue;
    }
    public static BlockingQueue<Object> getRes() {
        return resQueue;
    }
}
