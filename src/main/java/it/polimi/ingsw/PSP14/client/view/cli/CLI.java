package it.polimi.ingsw.PSP14.client.view.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import it.polimi.ingsw.PSP14.client.model.Color;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;
import it.polimi.ingsw.PSP14.server.model.Point;

public class CLI extends UI {
    private static final String CELL_S = "%d%s",
        HDELIM_S = "---------------",
        VDELIM_S = "|";

    private Scanner in = new Scanner(System.in);

    public Color getColor() {
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
        System.out.println("Choose your god:");
        List<String> godNames = proposals.stream().map(GodProposal::getName).collect(Collectors.toList());

        return choose(godNames);
    }

    @Override
    public int chooseAvailableGods(List<GodProposal> gods) {
        System.out.println("Choose gods for the match:");
        List<String> godNames = gods.stream().map(GodProposal::getName).collect(Collectors.toList());

        return choose(godNames);
    }

    @Override
    public int[] chooseWorkerInitialPosition() {
        System.out.println("Choose initial position for worker (es. 2 3):");
        int[] coordinates = new int[2];
        while(true) {
            try {
                String line = in.nextLine();
                String[] tokens = line.split(" ");
                coordinates[0] = Integer.parseInt(tokens[0]);
                coordinates[1] = Integer.parseInt(tokens[1]);
                if (coordinates[0] >= 0 && coordinates[0] < 5 && coordinates[1] >= 0 && coordinates[1] < 5) {
                    break;
                } else {
                    System.out.println("Coordinates out of range! (between 0 and 4 included)");
                }

            } catch (Exception e) {
                System.out.println("Input error!");
            }
        }

        return coordinates;
    }
}
