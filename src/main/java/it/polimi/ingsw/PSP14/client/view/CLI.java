package it.polimi.ingsw.PSP14.client.view;

import java.util.*;
import java.util.stream.Collectors;

import it.polimi.ingsw.PSP14.client.model.UICell;
import it.polimi.ingsw.PSP14.client.model.UIColor;
import it.polimi.ingsw.PSP14.client.model.UIPlayer;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;

public class CLI extends UI {
    private static final CLIHelper canvas = new CLIHelper(20, 70);

    private final int
            BOARD_START_X = 1,
            BOARD_START_Y = 1,
            BOARD_WIDTH = 25,
            BOARD_HEIGHT = 10,
            INFO_START_X = BOARD_START_X + BOARD_WIDTH + 3;
    /**
     * The user input. Use it to read what the user types in the console.
     */
    private final Scanner in = new Scanner(System.in);

    private void drawPlayerNames() {
        canvas.addRect(INFO_START_X, BOARD_START_Y, 40, BOARD_HEIGHT);
        canvas.addLine(INFO_START_X, BOARD_START_Y + 2, INFO_START_X + 40, BOARD_START_Y + 2);
        canvas.addText(INFO_START_X + 2, BOARD_START_Y + 1, "Players", CLIColor.RESET);
        int i = 0;
        for(UIPlayer player : cache.getPlayers()) {
            canvas.addText(INFO_START_X + 2, BOARD_START_Y + 3 + i,
                    (i+1) + ". " + player.getUsername(), (CLIColor) player.getColor());
            i += 1;
        }
    }

    private void drawBoard() {
        // Draw the outer edge of the board
        canvas.addRect(BOARD_START_X, BOARD_START_Y, BOARD_WIDTH, BOARD_HEIGHT);
        // Draw the rows separators
        for (int i = 0; i <= 4; i++) {
            int padding = BOARD_START_Y + 2 * i;
            canvas.addLine(BOARD_START_X, padding , BOARD_START_X + BOARD_WIDTH, padding);
        }
        // Draw the lines separators
        for (int i = 0; i <= 4; i++) {
            int padding = BOARD_START_X + 5*i;
            canvas.addLine(padding, BOARD_START_Y, padding, BOARD_START_Y + BOARD_HEIGHT);
        }
        // Draw the cell content
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                drawCell(x, y, cache.getCell(x,y));
            }
        }
    }

    /**
     * Draw a cell in 4 chars of space.
     * The first char will be the height of the
     * tower, the second char will be a "D" if
     * the tower has a dome, else it will be " ".
     * The third char is whether there's a worker
     * on the cell, marked by "W", else it will
     * be " ", and the last one is an ID for the
     * player.
     * @param x coordinate
     * @param y coordinate
     * @param cell The cell
     */
    private void drawCell(int x, int y, UICell cell) {
        int paddingLeft= BOARD_START_X + 1 + 5 * x;
        int paddingTop = BOARD_START_Y + 1 + 2 * y;
        StringBuilder output = new StringBuilder();
        output.append(cell.getTowerHeight());
        output.append(cell.hasDome() ? "D" : " ");
        output.append(cell.hasWorker() ? "W" : " ");
        // TODO: Use a UID
        output.append("X");
        canvas.addText(
                paddingLeft,
                paddingTop,
                output.toString(),
                cell.hasWorker()
                        ? (CLIColor) cell.getWorker().getPlayer().getColor()
                        : CLIColor.RED
        );
    }

    /* UI Methods */
    @Override
    public void update() {
        drawPlayerNames();
        drawBoard();
        canvas.print();
    }

    @Override
    public UIColor getColor() {
        CLIColor[] _colors = CLIColor.values();
        CLIColor _newColor = _colors[new Random().nextInt(_colors.length)];
//        System.out.println(_newColor.toString().substring(1));
        return _newColor;
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

