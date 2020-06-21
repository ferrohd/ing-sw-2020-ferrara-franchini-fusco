package it.polimi.ingsw.PSP14.client.view.cli;

/**
 * A class that offers a way to concatenate a char with a color escape sequence.
 */
public class ColorChar {
    private final char character;
    private final CLIColor color;

    /**
     * Constructor.
     * @param c the char you want to add color to
     * @param color the color
     */
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

    /**
     * @return a string sequence made up of the character if no color is set, and the
     * color escape sequence + character + reset color escape sequences if a color is set.
     */
    @Override
    public String toString() {
        if (color == null)
            return character + "";
        else
            return color.toString() + character + CLIColor.RESET;
    }
}
