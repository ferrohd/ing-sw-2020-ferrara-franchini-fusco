package it.polimi.ingsw.PSP14.client.view.cli;

import it.polimi.ingsw.PSP14.client.model.UICache;
import it.polimi.ingsw.PSP14.client.model.UICell;
import it.polimi.ingsw.PSP14.client.model.UIPlayer;

import java.util.List;
import java.util.stream.Collectors;

class ColorChar {
    private final char character;
    private final CLIColor color;

    ColorChar(char c, CLIColor color) {
        this.character = c;
        this.color = color == CLIColor.RESET
                ? null
                : color;
    }

    public char getChar() {
        return character;
    }

    public CLIColor getColor() { return color; }

    @Override
    public String toString() {
        if (color == null)
            return character + "";
        else
            return color.toString() + character + CLIColor.RESET;
    }
}

/**
 * Class that contains utility to draw better
 * Command Line Interfaces.
 */
public class CLIHelper {
    private static final int
            CANVAS_WIDTH = 70,
            CANVAS_HEIGHT = 13,
            BOARD_X = 3,
            BOARD_Y = 2,
            BOARD_WIDTH = 25,
            BOARD_HEIGHT = 10,
            GAP = 2,
            PANEL_START = BOARD_X + BOARD_WIDTH + GAP,
            PANEL_Y = 1,
            PANEL_WIDTH = CANVAS_WIDTH - BOARD_WIDTH - 10;


    int height, width;
    UICache cache;
    ColorChar[][] canvas;

    private void initCanvas() {
        canvas = new ColorChar[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                canvas[i][j] = new ColorChar('*', null);
            }
        }
    }

    /**
     * Setup a graphic space that will act like
     * a buffer for what will be printed in stdout
     * later on, allowing a greater control on shapes
     * and layout. If not height and width is specified,
     * assume the default 80x24 on the terminal.
     * @param cache ref to the local UICache
     */
    CLIHelper(UICache cache) {
        this.width = CANVAS_WIDTH;
        this.height = CANVAS_HEIGHT;
        this.cache = cache;
        initCanvas();
    }

    /**
     * Setup a graphic space that will act like
     * a buffer for what will be printed in stdout
     * later on, allowing a greater control on shapes
     * and layout.
     * @param height the height of the window
     * @param cache ref to the local UICache
     */
    CLIHelper(int height, UICache cache) {
        this.height = height;
        this.width = CANVAS_WIDTH;
        this.cache = cache;
        initCanvas();
    }

    /**
     * Reset the canvas to an empty state.
     * <br/>It's good habit to call it straight after
     * print() in order to prevent glitches.
     */
    private void reset() {
        initCanvas();
    }

    /**
     * Draw an ASCII rect on the canvas
     * @param x horizontal start position
     * @param y vertical start position
     * @param w width
     * @param h height
     */
    private void addRect(int x, int y, int w, int h) {
        addLine(x, y, x, y+h);
        addLine(x+w, y, x+w, y+h);
        addLine(x, y, x+w, y);
        addLine(x, y+h, x+w, y+h);
    }

    /**
     * Draw a horizontal or vertical line.
     * Lines that aren't horizontal or vertical
     * won't be drawn.
     * @param x1 start x
     * @param y1 start y
     * @param x2 end x
     * @param y2 end y
     */
    private void addLine(int x1, int y1, int x2, int y2) {
        // return if out of bounds
        if (!(0 <= x1 && x1 <= x2 && x2 <= width &&
            0 <= y1 && y1 <= y2 && y2 <= height)) return;
        // determine direction
        ColorChar fill;
        if (x1 == x2) {
            fill = new ColorChar('║', null);
        }
        else if (y1 == y2) {
            fill = new ColorChar('═', null);
        }
        else return; // we don't draw diagonal lines

        for (int row = y1; row <= y2; row++) {
            for (int col = x1; col <= x2; col++) {
                if (row < height && col < width)
                    if (canvas[row][col].getChar() == '*')
                        canvas[row][col] = fill;
            }
        }
    }

    /**
     * Add a text label of variable size to the canvas.
     * Overflowing text will be hidden.
     * @param x x coordinate
     * @param y y coordinate
     * @param text the input text
     * @param color the color of the text (default: RESET)
     */
    private void addText(int x, int y, String text, CLIColor color) {
        int textSize = text.length();
        if (!(0 <= x && x <= width &&
                0 <= y && y <= height)) return;

        for (int i = 0; i < textSize; i++) {
            char c = text.charAt(i);
            if (x+i < width)
                canvas[y][x+i] = new ColorChar(c, color);
        }
    }

    /**
     * Fix graphic glitches and prepare the
     * canvas for rendering.
     */
    private void refresh() {
        // Fix coupling between pipe chars
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                canvas[i][j] = new ColorChar(getSymbol(j, i), canvas[i][j].getColor());
            }
        }
        // Replace X
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (canvas[i][j].getChar() =='*') canvas[i][j] = new ColorChar( ' ', canvas[i][j].getColor());
            }
        }
    }

    /**
     * This method returns the correct ASCII symbol for
     * borders according to neighbouring cells.
     * @param x x coordinate
     * @param y y coordinate
     * @return an ASCII symbol
     */
    private char getSymbol(int x, int y) {
        boolean top, bottom, left, right;
        top = bottom = left = right = false;

        if ((int) canvas[y][x].getChar() < 128) return canvas[y][x].getChar();

        if (x < width-1) right = (int) canvas[y][x+1].getChar() >= 179;
        if (x > 0) left = (int) canvas[y][x-1].getChar() >= 179;
        if (y < height-1) bottom = (int) canvas[y+1][x].getChar() >= 179;
        if (y > 0) top = (int) canvas[y-1][x].getChar() >= 179;

        if (top) { // ╠ ╬ ╚ ╝ ╩ ║ ╣
            if (bottom) { // ╠ ╬ ║ ╣
                if (left) { // ╣ ╬
                    if (right) return '╬';
                    else return '╣';
                } else { // ╠ ║
                    if (right) return '╠';
                }
            } else { // ╚ ╝ ╩
                if (left) { // ╝ ╩
                    if (right) return '╩';
                    else return '╝';
                } else { // ╚
                    if (right) return '╚';
                }
            }
        } else { // ═ ╔ ╦ ╗
            if (bottom) { // ╔ ╦ ╗
                if (left) { // ╦ ╗
                    if (right) return '╦';
                    else return '╗';
                } else {
                    if (right) return '╔';
                }
            } else return '═';
        }
        return '║';
    }

    /**
     * Print the canvas to the standard output
     */
    public void print() {
        clear();
        refresh();

        for (int row = 0; row < height; row++) {
            ColorChar[] line = canvas[row];
            StringBuilder sb = new StringBuilder();
            for (int col = 0; col < width; col++) {
                ColorChar chad = line[col];
                sb.append(chad.toString());
            }
            System.out.println(sb.toString());
        }
        System.out.print(CLIColor.RESET);
    }

    /**
     * Handle the cleaning of the console on windows and other OSs.
     * @see <a href="https://stackoverflow.com/questions/2979383/java-clear-the-console">Stackoverflow: Clear the console</a>
     */
    public static void clear() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception e) {
            // Do nothing, since we're not clearing the console.
        }
    }

    private void drawBoard() {
        // Draw the outer edge of the board
        addRect(BOARD_X, BOARD_Y, BOARD_WIDTH, BOARD_HEIGHT);
        // Draw the rows separators
        for (int i = 0; i <= 4; i++) {
            int padding = BOARD_Y + 2 * i;
            addLine(BOARD_X, padding , BOARD_X + BOARD_WIDTH, padding);
        }
        // Draw the lines separators
        for (int i = 0; i <= 4; i++) {
            int padding = BOARD_X + 5*i;
            addLine(padding, BOARD_Y, padding, BOARD_Y + BOARD_HEIGHT);
        }
        // Draw numbers
        // draw X
        for (int i = 0; i < 5; i++) {
            addText(BOARD_X + 2 + i * 5, BOARD_Y - 1, i + "", CLIColor.RESET);
        }
        // draw Y
        for (int i = 0; i < 5; i++) {
            addText(BOARD_X - 2, BOARD_Y + 1 + i * 2, (char)('A' + i) + "", CLIColor.RESET);
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
        int paddingLeft= BOARD_X + 1 + 5 * x;
        int paddingTop = BOARD_Y + 1 + 2 * y;

        String output = cell.getTowerHeight() +
                (cell.hasDome() ? "D" : " ") +
                (cell.hasWorker() ? "W" + cell.getWorker().getPlayer().getNumber() : "  ");
        addText(
                paddingLeft,
                paddingTop,
                output,
                cell.hasWorker()
                        ? (CLIColor) cell.getWorker().getPlayer().getColor()
                        : CLIColor.RESET
        );
    }

    /**
     * Display a list that splits in two columns if <code>list.size() > height</code>
     * @param x start
     * @param y start
     * @param w width
     * @param h height
     * @param list list of choices
     * @param colors colors associated with the choices - set <code>null</code> if you want standard text
     */
    public void drawList(int x, int y, int w, int h, List<String> list, List<CLIColor> colors) {
        for (int i = 0; i < list.size(); i++) {
            int _x = i < h ? x : x + w / 2;
            int _y = i < h ? y + i : y + i - h;
            addText(_x, _y, (i+1) + ". " + list.get(i), colors != null ? colors.get(i) : CLIColor.RESET);
        }
    }

    public void drawBoardAndPlayers() {
        List<UIPlayer> _players = cache.getPlayers();
        reset();
        clear();
        drawBoard();
        addText(PANEL_START, PANEL_Y, "PLAYERS:", CLIColor.RESET);
        drawList(
                PANEL_START + 1,
                PANEL_Y + 1,
                PANEL_WIDTH,
                3,
                _players.stream().map(UIPlayer::getUsername).collect(Collectors.toList()),
                _players.stream().map(p -> (CLIColor) p.getColor()).collect(Collectors.toList())
        );
    }

    /**
     * Draw options on the right side of the map/board.
     * This will be useful for either workers or actions.
     * @param choices list of strings representing the entries.
     */
    public void drawPanelChoices(String title, List<String> choices) {
        addText(PANEL_START, PANEL_Y + 5, title, CLIColor.RESET);
        drawList(
                PANEL_START + 1,
                PANEL_Y + 6,
                PANEL_WIDTH,
                5,
                choices,
                null
        );
    }

    /**
     * Add a text at the top of the screen.
     * Reset and print included.
     * @param text the message
     */
    public void drawMessageFullscreen(String text) {
        reset();
        clear();
        addRect(0,0,CANVAS_WIDTH-1, 3);
        addText(2, 1, text, CLIColor.RESET);
        print();
        reset();
    }

    /**
     * Draw a list with a heading fullscreen.
     * The entries will have a prefix starting from 1.
     * @param title Prompt at the top
     * @param list List of choices (strings).
     */
    public void drawListFullscreen(String title, List<String> list) {
        reset();
        clear();
        addText(BOARD_X, BOARD_Y, title, CLIColor.RESET);
        drawList(BOARD_X, BOARD_Y+1, CANVAS_WIDTH - BOARD_X, BOARD_HEIGHT-1, list, null);
        print();
        reset();
    }

    public void drawMessage(String text) {
        addText(BOARD_X, BOARD_Y+BOARD_HEIGHT+1, text, CLIColor.YELLOW);
    }
}

