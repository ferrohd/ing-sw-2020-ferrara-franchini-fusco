package it.polimi.ingsw.PSP14.client.view.cli;

/**
 * Enum representing available colors to be shown in the CLI.
 * Every color has a corresponding escape sequence.
 */
enum CLIColor implements UIColor {
    RESET("\033[0m"),
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    YELLOW("\033[0;33m"),
    BLUE("\033[0;34m"),
    MAGENTA("\033[0;35m"),
    CYAN("\033[0;36m");

    private final String code;

    CLIColor(String code) {
        this.code = code;
    }

    /**
     * @return the escape sequence to be printed.
     */
    public String toString() {
        return code;
    }
}
