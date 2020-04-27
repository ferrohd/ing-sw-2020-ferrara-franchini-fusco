package it.polimi.ingsw.PSP14.client.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;
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
    public void notifyConnection(String hostname, int port) {
        notify("Connecting to " + hostname + " at port " + port + "...");
    }

    @Override
    public int getLobbySize() {
        String choice;
        do {
            System.out.println("How many players? (2 or 3)");
            choice = in.nextLine();
        } while(!choice.equals("2") && !choice.equals("3"));

        return Integer.parseInt(choice);
    }

    @Override
    public void notify(String s) {
        System.out.println(s);
    }

    @Override
    public String askUsername() {
        System.out.println("Insert username:");
        return in.nextLine();
    }

    private int choose(List<String> options) {
        for(int i = 0; i < options.size(); ++i) {
            System.out.println(i + ") " + options.get(i));
        }

        int choice;
        while(true) {
            String line = in.nextLine();
            try {
                choice = Integer.parseInt(line);
                if(choice < 0 || choice >= options.size()) throw new NumberFormatException();
                return choice;
            } catch (NumberFormatException e) {
                System.out.println("Please insert a number between 0 and " + (options.size()-1) + ".");
            }
        }
    }

    @Override
    public int chooseFirstPlayer(List<PlayerProposal> proposals) {
        System.out.println("Choose the player who goes first:");
        List<String> names = proposals.stream().map(PlayerProposal::getName).collect(Collectors.toList());

        return choose(names);
    }

    @Override
    public int chooseWorker() {
        System.out.println("Choose the worker to move:");
        List<String> options = new ArrayList<String>();
        options.add("0");
        options.add("1");

        return choose(options);
    }

    @Override
    public int chooseGod(List<GodProposal> proposals) {
        // TODO: Implement method
        return 0;
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
