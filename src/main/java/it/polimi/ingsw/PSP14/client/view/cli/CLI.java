package it.polimi.ingsw.PSP14.client.view.cli;

import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CLI implements UI {

    private final UICache cache = new UICache();
    private final Set<UIColor> assignedColors = new HashSet<>();
    private int playerNumber = 1;
    private final CLIHelper ctx = new CLIHelper(cache);
    private String playerUsername;
    private final Scanner in = new Scanner(System.in);

    @Override
    public void gameStart() throws InterruptedException {

    }

    @Override
    public void startWorkerChoice(String player) throws InterruptedException {
        if(player.equals(playerUsername)) {
            showNotification("Choose the worker you would like to move");
        } else {
            showNotification(player + " is choosing the worker to move");
        }
    }

    @Override
    public void startMove(String player) throws InterruptedException {
        if(player.equals(playerUsername)) {
            showNotification("Choose where to move");
        } else {
            showNotification(player + " is choosing where to move");
        }
    }

    @Override
    public void startBuild(String player) throws InterruptedException {
        if(player.equals(playerUsername)) {
            showNotification("Choose where to build");
        } else {
            showNotification(player + " is choosing where to build");
        }
    }

    @Override
    public void startWorkerPlacement(String player) throws InterruptedException {
        if(player.equals(playerUsername)) {
            showNotification("Choose where to place your workers");
        } else {
            showNotification(player + " is choosing where to place their workers");
        }
    }

    @Override
    public void registerPlayer(String newPlayerUsername) {
        UIColor _newPlayerColor = null;
        // Prevent duplicate colors
        while (_newPlayerColor == null ||
                assignedColors.contains(_newPlayerColor)) {
            _newPlayerColor = getColor(playerNumber);
        }
        assignedColors.add(_newPlayerColor);

        cache.addPlayer(newPlayerUsername, playerNumber++, _newPlayerColor);
    }

    @Override
    public void unregisterPlayer(String username) {
        cache.removePlayer(username);
    }

    @Override
    public void setWorker(Point position, int workerId, String playerUsername) {
        UIPlayer _player = cache.getPlayer(playerUsername);
        UIWorker _worker = _player.getWorker(workerId);
        // Create a new worker if there isn't one
        if (_worker == null) {
            _worker = new UIWorker(workerId, _player);
        }
        cache.setWorker(_worker, playerUsername, cache.getCell(position));
    }

    private void unsetWorker(int workerId, String playerUsername) {
        UIPlayer _player = cache.getPlayer(playerUsername);
        cache.unsetWorker(_player.getWorker(workerId));
    }

    @Override
    public void moveWorker(Point newPosition, int workerId, String playerUsername) {
        unsetWorker(workerId, playerUsername);
        setWorker(newPosition, workerId, playerUsername);
    }

    @Override
    public void incrementCell(Point position) {
        cache.getCell(position).incrementTowerHeight();
    }

    @Override
    public void setDome(Point position) {
        cache.getCell(position).setDome(true);
    }

    public UIColor getColor() {
        CLIColor[] colorList = CLIColor.values();
        CLIColor toRet;
        while((toRet = colorList[new Random().nextInt(colorList.length)]) == CLIColor.RESET);
        return toRet;
    }

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

    private int parseCoordinates(Consumer<String> callback, List<Point> validCoordinates) throws IOException {
        String _input;
        do {
            callback.accept("");
            _input = in.nextLine();
            if (_input.equals("help")) {
                Map<String, UIGod> godList = GodFactory.getInstance().getGodsMap();
                godList.forEach( (name, god) -> { this.drawMessage(god.getName() + ": " + god.getDescription(), CLIColor.RED); } );
            }
        } while (!_input.matches("^[a-e]|[A-E]\\s[0-4]$"));
        String[] _coords = _input.split(" ");
        int _x = _coords[1].charAt(0) - '0';
        int _y = _coords[0].toLowerCase().charAt(0) - 'a';
        for (int i = 0; i < validCoordinates.size(); i++) {
            Point coord = validCoordinates.get(i);
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
    
    /**
     * Use this to draw a fullscreen message.
     * @param text the text to draw
     * @param color color of the drawing
     */
    private void drawMessage(String text, CLIColor color) {
        System.out.println(color + "> " + text + CLIColor.RESET);
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
    public void showVictory(String winner) {
        showNotification(winner + " won!");
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
    public int chooseWorker(List<Integer> choosable) throws IOException {
        // Get this player name and get his worker details
        List<UIWorker> _pw = cache.getPlayer(this.playerUsername).getWorkers();
        List<Point> workersPosition = _pw.stream().map(w -> w.getCell().getPoint()).collect(Collectors.toList());
        List<String> _workers = _pw.stream()
                .filter(w -> choosable.contains(w.getId()))
                .map(w -> "[" + (char)(w.getCell().getY()+'A') + " " + w.getCell().getX() + "]")
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
    public int chooseMove(List<MoveProposal> moves) throws IOException {
        List<String> moveStrings = moves.stream()
                .map(m -> "[" + (char)(m.getPoint().getY() +'A') + " " + m.getPoint().getX() + "]")
                .collect(Collectors.toList());
        List<Point> validMoves = moves.stream().map(m -> m.getPoint()).collect(Collectors.toList());

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
    public int chooseBuild(List<BuildProposal> moves) throws IOException {
        List<String> buildStrings = moves.stream()
                .map(m -> "[" + (char)(m.getPoint().getY() +'A') + " " + m.getPoint().getX() + "]" + (m.hasDome() ? " (dome)" : ""))
                .collect(Collectors.toList());
        List<Point> buildPositions = moves.stream().map(m -> m.getPoint()).collect(Collectors.toList());

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

    @Override
    public void updateGod(String player, String god) {
        // TODO: Should it do something?
    }

    // DEBUG:
    public void debug_setPlayerUsername(String name) {
        this.playerUsername = name;
    }
}