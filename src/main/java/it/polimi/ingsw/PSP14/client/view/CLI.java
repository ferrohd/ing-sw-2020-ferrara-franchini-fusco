package it.polimi.ingsw.PSP14.client.view;

import java.util.Random;
import java.util.Scanner;

import it.polimi.ingsw.PSP14.server.model.Point;

public class CLI extends UI {
    private static final String CELL_S = "%d%s",
        HDELIM_S = "---------------",
        VDELIM_S = "|";

    private Scanner in = new Scanner(System.in);

    CLIColor getColor() {
        CLIColor[] colorList = CLIColor.values();
        CLIColor toRet;
        while((toRet = colorList[new Random().nextInt(colorList.length)]) == CLIColor.RESET);

        return toRet;
    }

    public void update() {
        StringBuilder sbuf = new StringBuilder();
        sbuf.append(CLIColor.RESET).append(HDELIM_S).append("\n");
        for(int j = 0; j < 5; ++j) {
            sbuf.append(VDELIM_S);
            for(int i = 0; i < 5; ++i) {
                sbuf.append(getCellString(new Point(i, j))).append(VDELIM_S);
            }
            sbuf.append("\n");
        }
        sbuf.append(HDELIM_S).append("\n");

        System.out.print(sbuf);
    }

    private String getCellString(Point pos) {
        String towerString = getCache().getBlock(pos).getHasDome() ?
                "X" :
                Integer.toString(getCache().getBlock(pos).getSize());
        String workerString = getCache().getBlock(pos).hasWorker() ?
                getColorMap().get(getCache().getBlock(pos).getWorker()) + "W" + CLIColor.RESET :
                " ";

        return towerString + workerString;
    }

    @Override
    public void welcome() {
        System.out.println("Welcome to SANTORINI!");
    }

    @Override
    public void noticeConnecting(String hostname, int port) {
        System.out.println("Connecting to " + hostname + " at port " + port);
    }

    @Override
    public int getRoomSize() {
        int choice;
        do {
            System.out.println("How many players? (2 or 3)");
            choice = in.nextInt();
        } while(choice != 2 && choice != 3);

        return choice;
    }
}

enum CLIColor implements Color {
    RESET("\033[0m"),
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    YELLOW("\033[0;33m"),
    BLUE("\033[0;34m"),
    MAGENTA("\033[0;35m"),
    CYAN("\033[0;36m");

    private String code;

    CLIColor(String code) {
        this.code = code;
    }

    public String toString() {
        return code;
    }
}
