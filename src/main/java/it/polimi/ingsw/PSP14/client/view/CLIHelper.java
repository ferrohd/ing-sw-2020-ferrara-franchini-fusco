package it.polimi.ingsw.PSP14.client.view;

// D stay for... hmmm...
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
    int height, width;

    ColorChar[][] canvas;

    private void initCanvas() {
        canvas = new ColorChar[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                canvas[i][j] = new ColorChar('x', null);
            }
        }
    }

    /**
     * Setup a graphic space that will act like
     * a buffer for what will be printed in stdout
     * later on, allowing a greater control on shapes
     * and layout. If not height and width is specified,
     * assume the default 80x24 on the terminal.
     */
    CLIHelper() {
        this.width = 80;
        this.height = 15;
        initCanvas();
    }

    /**
     * Setup a graphic space that will act like
     * a buffer for what will be printed in stdout
     * later on, allowing a greater control on shapes
     * and layout.
     * @param height the height of the window
     * @param width the width of the window
     */
    CLIHelper(int height, int width) {
        this.height = height;
        this.width = width;
        initCanvas();
    }

    /**
     * Draw an ASCII rect on the canvas
     * @param x horizontal start position
     * @param y vertical start position
     * @param w width
     * @param h height
     */
    public void addRect(int x, int y, int w, int h) {
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
    public void addLine(int x1, int y1, int x2, int y2) {
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
                    if (canvas[row][col].getChar() == 'x')
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
    public void addText(int x, int y, String text, CLIColor color) {
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
                if (canvas[i][j].getChar() =='x') canvas[i][j] = new ColorChar( ' ', canvas[i][j].getColor());
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
}

