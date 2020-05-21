package it.polimi.ingsw.PSP14.client.view.cli;

public class ColorChar {
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
