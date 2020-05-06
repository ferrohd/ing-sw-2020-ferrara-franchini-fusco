package it.polimi.ingsw.PSP14.client.view;

import java.util.*;
import java.util.stream.Collectors;

import it.polimi.ingsw.PSP14.client.model.UICell;
import it.polimi.ingsw.PSP14.client.model.UIColor;
import it.polimi.ingsw.PSP14.client.model.UIPlayer;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;

public class CLI extends UI {

    private static int
            CANVAS_WIDTH = 70,
            CANVAS_HEIGHT = 15;
    private static final int
            BOARD_START_X = 3,
            BOARD_START_Y = 2,
            BOARD_WIDTH = 25,
            BOARD_HEIGHT = 10,
            INFO_START_X = BOARD_START_X + BOARD_WIDTH + 3,
            INFO_WIDTH = 24;

    private static final CLIHelper canvas = new CLIHelper(CANVAS_HEIGHT, CANVAS_WIDTH);
    /**
     * The user input. Use it to read what the user types in the console.
     */
    private final Scanner in = new Scanner(System.in);

    private void drawPlayerNames() {
        canvas.addRect(INFO_START_X, BOARD_START_Y, INFO_WIDTH, BOARD_HEIGHT);
        canvas.addLine(INFO_START_X, BOARD_START_Y + 2, INFO_START_X + INFO_WIDTH, BOARD_START_Y + 2);
        canvas.addText(INFO_START_X + 2, BOARD_START_Y + 1, "Players", CLIColor.RESET);
        int i = 0;
        for(UIPlayer player : cache.getPlayers()) {
            canvas.addText(INFO_START_X + 2, BOARD_START_Y + 3 + i,
                    player.getNumber() + ". " + player.getUsername(), (CLIColor) player.getColor());
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
        // Draw numbers
        for (int i = 0; i < 5; i++) {
            canvas.addText(3 + i * 5, BOARD_START_Y - 1, i + "", CLIColor.RESET);
        }
        for (int i = 0; i < 5; i++) {
            canvas.addText(BOARD_START_X - 2, BOARD_START_Y + 1 + i * 2, i + "", CLIColor.RESET);
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

        String output = cell.getTowerHeight() +
                (cell.hasDome() ? "D" : " ") +
                (cell.hasWorker() ? "W" + cell.getWorker().getPlayer().getNumber() : "  ");
        canvas.addText(
                paddingLeft,
                paddingTop,
                output,
                cell.hasWorker()
                        ? (CLIColor) cell.getWorker().getPlayer().getColor()
                        : CLIColor.RESET
        );
    }

    /**
     * Add a text at the top of the screen
     * @param text the message
     */
    private void drawMessage(String text) {
        canvas.reset();
        canvas.addRect(0,0,CANVAS_WIDTH-1, 3);
        canvas.addText(2, 1, text, CLIColor.RESET);
        canvas.print();
        canvas.reset();
    }

    /**
     * Display a list of options and let the user choose from it.
     * @param options list of options (strings)
     * @return the index of the chosen element
     */
    private int choose(String title, List<String> options) {
        canvas.reset();
        boolean _displayWarning = true;
        int choice;
        while(true) {
            // Print the box with all the options
            // If we have more than 9 elements split in 2 columns
            int _h = options.size() <= 8
                    ? options.size() + 2
                    : 10 + 1;
            canvas.reset();
            canvas.addRect(0,0,CANVAS_WIDTH - 1, _h);
            canvas.addText(3, 1, title, CLIColor.YELLOW);
            for(int i = 0; i < options.size(); i++) {
                int _x = i <= 8
                        ? 3
                        : CANVAS_WIDTH/2;
                int _y = i <= 8
                        ? 2+i
                        : 2+i-9;
                canvas.addText(_x, _y,(i+1) + ". " + options.get(i), CLIColor.RESET);
            }
            canvas.print();
            canvas.reset();
            String line = in.nextLine();
            try {
                choice = Integer.parseInt(line);
                choice -= 1; // We start at 1 in printed part but at 0 in arrays.
                if(choice < 0 || choice >= options.size()) throw new NumberFormatException();
                return choice;
            } catch (NumberFormatException ignored) {
                if (_displayWarning) {
                    title = "Invalid Input! " + title;
                    _displayWarning = false;
                }
            }
        }
    }

    /**
     * Add a text at the bottom of the board.
     * <br/> This method does not call
     * <code>canvas.print()</code>.
     * @param text The content of the message
     * @param color The color of the message
     */
    private void drawMessageUnderBoard(String text, CLIColor color) {
        canvas.addText(BOARD_START_X + 1,
                BOARD_START_Y + BOARD_HEIGHT + 1,
                text,
                color);
    }

    /* UI Methods */
    @Override
    public void update() {
        drawPlayerNames();
        drawBoard();
        canvas.print();
        canvas.reset();
    }

    @Override
    public UIColor getColor() {
        CLIColor[] _colors = CLIColor.values();
        List<CLIColor> _filteredColors = new ArrayList<>();
        for (CLIColor _color : _colors) {
            if (_color != CLIColor.RESET) _filteredColors.add(_color);
        }
        return _filteredColors.get(new Random().nextInt(_filteredColors.size()));
    }

    @Override
    public void welcome() {
        drawMessage("Welcome to SANTORINI!");
    }

    @Override
    public void notifyConnection(String hostname, int port) {
        drawMessage("Connecting to " + hostname + " at port " + port + "...");
    }

    @Override
    public int getLobbySize() {
        String choice;
        do {
            drawMessage("How many players you want in the match? {2, 3}");
//            System.out.println("How many players? (2 or 3)");
            choice = in.nextLine();
        } while(!choice.equals("2") && !choice.equals("3"));

        return Integer.parseInt(choice);
    }

    @Override
    public void notify(String s) {
        drawMessage(s);
//        System.out.println(s);
    }

    @Override
    public String askUsername() {
        drawMessage("Insert your username:");
//        System.out.println("Insert username:");
        return in.nextLine();
    }

    @Override
    public int chooseFirstPlayer(List<PlayerProposal> proposals) {
        List<String> names = proposals.stream().map(PlayerProposal::getName).collect(Collectors.toList());

        return choose("Choose the player who goes first:", names);
    }

    @Override
    public int chooseWorker() {
        List<String> options = new ArrayList<String>();
        options.add("0");
        options.add("1");

        return choose("Choose the worker to move:", options);
    }

    @Override
    public int chooseGod(List<GodProposal> proposals) {
        List<String> godNames = proposals.stream().map(GodProposal::getName).collect(Collectors.toList());

        return choose("Select your god:", godNames);
    }

    @Override
    public int chooseAvailableGods(List<GodProposal> gods) {
        List<String> godNames = gods.stream().map(GodProposal::getName).collect(Collectors.toList());

        return choose("Select available gods to pick for the match:", godNames);
    }

    @Override
    public int[] chooseWorkerInitialPosition() {
        drawMessageUnderBoard("Select initial position for worker (es. 2 3):", CLIColor.YELLOW);
        update();
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
                    drawMessageUnderBoard("Coordinates out of range! (Choose between 0 1 2 3 4)", CLIColor.RED);
                }
            } catch (Exception e) {
                drawMessageUnderBoard("Invalid Input! Try to insert only two numbers separated by a whitespace.", CLIColor.RED);
            }
            update();
        }

        return coordinates;
    }
}

