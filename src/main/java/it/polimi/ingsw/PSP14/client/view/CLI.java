package it.polimi.ingsw.PSP14.client.view;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import it.polimi.ingsw.PSP14.client.model.UIColor;
import it.polimi.ingsw.PSP14.client.model.UIPlayer;
import it.polimi.ingsw.PSP14.client.model.UIWorker;
import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;

public class CLI extends UI {

    private final CLIHelper canvas = new CLIHelper(cache);
    /**
     * The user input. Use it to read what the user types in the console.
     */
    private String playerUsername;
    private final Scanner in = new Scanner(System.in);

    private int parseSingleInput(Consumer<String> callback) {
        int _choice = 0;
        while (_choice == 0) {
            callback.accept("");
            try {
                _choice = in.nextInt();
            } catch (Exception e) {
                callback.accept("Invalid Input!");
            }
        }
        return _choice - 1; // shift from 1-based entries to 0-based arrays
    }

    /**
     * Use this to draw a fullscreen message.
     * @param text the text to draw
     */
    private void drawMessage(String text) {
        canvas.drawMessageFullscreen(text);
    }

    private int chooseFullscreen(String prompt, List<String> options) {
        return parseSingleInput((prefix) -> canvas.drawListFullscreen(prefix + prompt, options));
    }

    private int choose(String prompt, List<String> options) {
        return parseSingleInput((prefix) -> {
            canvas.drawBoardAndPlayers();
            canvas.drawPanelChoices(prompt, options);
            canvas.print();
        });
    }

    /* UI Methods */
    @Override
    public void update() {
        canvas.drawBoardAndPlayers();
        canvas.print();
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
            choice = in.nextLine();
        } while(!choice.equals("2") && !choice.equals("3"));

        return Integer.parseInt(choice);
    }

    @Override
    public void notify(String text) {
        drawMessage(text);
    }

    @Override
    public String askUsername() {
        drawMessage("Insert your username:");
        String _username = in.nextLine();
        this.playerUsername = _username;
        return _username;
    }

    @Override
    public int chooseFirstPlayer(List<PlayerProposal> proposals) {
        List<String> names = proposals.stream().map(PlayerProposal::getName).collect(Collectors.toList());
        // TODO: Add colors?
        return chooseFullscreen("Choose the player who goes first:", names);
    }

    @Override
    public int chooseWorker() {
        // TODO: Get this player name and get his worker details
        List<UIWorker> _pw = cache.getPlayer(this.playerUsername).getWorkers();
        List<String> _workers = _pw.stream()
                .map(w -> "#" + w.getId() + " - (X: " + w.getCell().getX() + ", Y: " + w.getCell().getY()+")")
                .collect(Collectors.toList());

        return choose("Choose the worker to move:", _workers);
    }

    @Override
    public int chooseGod(List<GodProposal> proposals) {
        List<String> godNames = proposals.stream().map(GodProposal::getName).collect(Collectors.toList());

        return chooseFullscreen("Select your god:", godNames);
    }

    @Override
    public int chooseAvailableGods(List<GodProposal> gods) {
        List<String> godNames = gods.stream().map(GodProposal::getName).collect(Collectors.toList());

        return chooseFullscreen("Select available gods to pick for the match:", godNames);
    }

    @Override
    public int[] chooseWorkerInitialPosition() {
        canvas.drawBoardAndPlayers();
        canvas.drawMessage("Select worker initial position (x,y): (i.e.: \"2 3\")");
        canvas.print();

        int[] coordinates = new int[2];
        while(true) {
            String _msg;
            try {
                String line = in.nextLine();
                String[] tokens = line.split(" ");
                coordinates[0] = Integer.parseInt(tokens[0]);
                coordinates[1] = Integer.parseInt(tokens[1]);
                if (coordinates[0] >= 0 && coordinates[0] < 5 && coordinates[1] >= 0 && coordinates[1] < 5) {
                    break;
                } else {
                    _msg = "Coordinates out of range! (Choose between 0 < x|y < 4)";
                }
            } catch (Exception e) {
                _msg = "Invalid Input! Try to insert only two numbers separated by a whitespace.";
            }
            canvas.drawBoardAndPlayers();
            canvas.drawMessage(_msg);
            canvas.print();
        }

        return coordinates;
    }

    @Override
    public int chooseMove(List<MoveProposal> moves) {
        List<String> moveStrings = moves.stream().map(m -> m.getPoint().toString()).collect(Collectors.toList());

        return choose("Choose where to move:", moveStrings);
    }

    @Override
    public int chooseBuild(List<BuildProposal> moves) {
        List<String> buildStrings = moves.stream().map(m -> m.getPoint().toString() + (m.hasDome() ? "(dome)" : "")).collect(Collectors.toList());

        return choose("Choose where to build:", buildStrings);
    }

    // DEBUG:
    public void debug_setPlayerUsername(String name) {
        this.playerUsername = name;
    }
}

