package it.polimi.ingsw.PSP14.client.view;

import it.polimi.ingsw.PSP14.client.model.UIColor;
import it.polimi.ingsw.PSP14.client.model.UIPoint;
import it.polimi.ingsw.PSP14.client.model.UIWorker;
import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CLI extends UI {

    private final CLIHelper ctx = new CLIHelper(cache);

    private String playerUsername;
    private final Scanner in = new Scanner(System.in);

    private int parseInteger(Consumer<String> callback, int maxValue) {
        String _input;
        int _i = 0;
        do {
            callback.accept("");
            _input = in.nextLine();
            if (_input.matches("^\\d+$")) {
                try {
                    _i = Integer.parseInt(_input);
                } catch (Exception ignored) {}
                if (_i >= 1 && _i <= maxValue) break;
            }
        } while (true);
        return _i - 1;
    }

    private int parseCoordinates(Consumer<String> callback, List<UIPoint> validCoordinates) {
        String _input;
        do {
            callback.accept("");
            _input = in.nextLine();
        } while (!_input.matches("^[a-e]|[A-E]\\s[0-4]$"));
        String[] _coords = _input.split(" ");
        int _x = _coords[1].charAt(0) - '0';
        int _y = _coords[0].toLowerCase().charAt(0) - 'a';
        for (int i = 0; i < validCoordinates.size(); i++) {
            UIPoint coord = validCoordinates.get(i);
            if (coord.getX() == _x && coord.getY() == _y) return i;
        }
        // ask server to send again the command
        return -1;
    }

    /**
     * Use this to draw a fullscreen message.
     * @param text the text to draw
     */
    private void drawMessage(String text) {
        System.out.println(CLIColor.YELLOW + "> " + text + CLIColor.RESET);
    }

    @Override
    public UIColor getColor(int playerNumber) {
        CLIColor _color;
        switch (playerNumber) {
            case 1:
                _color = CLIColor.GREEN;
                break;
            case 2:
                _color = CLIColor.RED;
                break;
            case 3:
                _color = CLIColor.BLUE;
                break;
            default:
                _color = CLIColor.CYAN;
                break;
        }
        return _color;
    }

    /* UI Methods */
    @Override
    public void update() {
        ctx.drawBoardAndPlayers();
        ctx.print();
    }

    @Override
    public void welcome() {
        drawMessage("Welcome to SANTORINI!");
    }

    @Override
    public void showNotification(String text) {
        drawMessage(text);
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
    public String askUsername() {
        String _username;
        do {
            drawMessage("Insert your username:");
            _username = in.nextLine();
        } while (_username.length() <= 1);

        this.playerUsername = _username;
        return _username;
    }

    @Override
    public int chooseFirstPlayer(List<PlayerProposal> proposals) {
        List<String> names = proposals.stream().map(PlayerProposal::getName).collect(Collectors.toList());
        // TODO: Add colors?
        return parseInteger((err) -> ctx.drawListFullscreen(err + "Choose the player who goes first:", names), names.size());
    }

    @Override
    public int chooseGod(List<GodProposal> proposals) {
        List<String> godNames = proposals.stream().map(GodProposal::getName).collect(Collectors.toList());

        return parseInteger((err) -> ctx.drawListFullscreen(err + "Select your god:", godNames), godNames.size());
    }

    @Override
    public int chooseAvailableGods(List<GodProposal> gods) {
        List<String> godNames = gods.stream().map(GodProposal::getName).collect(Collectors.toList());

        return parseInteger((err) -> ctx.drawListFullscreen(err + "Select available gods to pick for the match:", godNames), godNames.size());
    }

    @Override
    public int[] chooseWorkerInitialPosition() {
        int[] coordinates = new int[2];
        String _msg = "";
        do {
            ctx.drawBoardAndPlayers();
            ctx.print();
            drawMessage("Select worker initial position: "
                    + CLIColor.CYAN
                    + "[insert coordinates like \"A 0\"]"
                    + "\n" + CLIColor.RED + _msg + CLIColor.RESET);
            try {
                String line = in.nextLine().toLowerCase();
                String[] tokens = line.split(" ");
                coordinates[1] = tokens[0].charAt(0) - 'a'; // Y (char) goes to coordinates[1]
                coordinates[0] = Integer.parseInt(tokens[1]); // X (number) goes to coordinates[0]
                if (coordinates[0] >= 0 && coordinates[0] <= 4 // 0 <= X <= 4
                        && coordinates[1] >= 0 && coordinates[1] <= 4) {
                    break;
                } else {
                    _msg = "Coordinates out of range! (Choose between 0 < x|y < 4)";
                }
            } catch (Exception e) {
                _msg = "Invalid Input! Try to insert only two numbers separated by a whitespace.";
            }
        } while (true);

        return coordinates;
    }

    @Override
    public int chooseWorker() {
        // Get this player name and get his worker details
        List<UIWorker> _pw = cache.getPlayer(this.playerUsername).getWorkers();
        List<UIPoint> workersPosition = _pw.stream().map(w -> w.getCell().getUIPoint()).collect(Collectors.toList());
        List<String> _workers = _pw.stream()
                .map(w -> "#" + w.getId() + " - (X: " + w.getCell().getX() + ", Y: " + (char)(w.getCell().getY()+'A') +")")
                .collect(Collectors.toList());

        ctx.drawBoardAndPlayers();
        ctx.drawPanelChoices("WORKERS", _workers);
        ctx.print();
        return parseCoordinates((err) -> drawMessage(err
                        + "Choose the worker you want to move: "
                        + CLIColor.CYAN
                        + "[insert coordinates like \"A 0\"]"),
                workersPosition);
    }

    @Override
    public int chooseMove(List<MoveProposal> moves) {
        List<String> moveStrings = moves.stream().map(m -> m.getPoint().toString()).collect(Collectors.toList());
        List<UIPoint> validMoves = moves.stream().map(m -> m.getPoint().getUIPoint()).collect(Collectors.toList());

        ctx.drawBoardAndPlayers();
        ctx.drawPanelChoices("MOVES", moveStrings);
        ctx.print();
        return parseCoordinates((err) -> drawMessage(err
                        + "Choose where to move: "
                        + CLIColor.CYAN
                        + "[insert coordinates like \"A 0\"]"),
                validMoves);
    }

    @Override
    public int chooseBuild(List<BuildProposal> moves) {
        List<String> buildStrings = moves.stream().map(m -> m.getPoint().toString() + (m.hasDome() ? "(dome)" : "")).collect(Collectors.toList());
        List<UIPoint> buildPositions = moves.stream().map(m -> m.getPoint().getUIPoint()).collect(Collectors.toList());

        ctx.drawBoardAndPlayers();
        ctx.drawPanelChoices("BUILDING OPTIONS", buildStrings);
        ctx.print();
        return parseCoordinates((err) -> drawMessage(err
                        + "Choose where to build: "
                        + CLIColor.CYAN
                        + "[insert coordinates like \"A 0\"]"),
                    buildPositions);
    }

    @Override
    public int chooseYesNo(String message) {
        drawMessage(message + CLIColor.CYAN + " [Answer \"Yes\" or \"No\"]");
        int output = -1;
        do {
            String input = in.nextLine();
            String[] tokens = input.toLowerCase().split(" ");
            if (tokens.length >= 1) {
                output = tokens[0].equals("yes") || tokens[0].equals("y")
                        ? 1
                        : tokens[0].equals("no") || tokens[0].equals("n")
                        ? 0
                        : -1;
            }
        } while (output < 0);

        return output;
    }

    // DEBUG:
    public void debug_setPlayerUsername(String name) {
        this.playerUsername = name;
    }
}
