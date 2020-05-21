package it.polimi.ingsw.PSP14.client.view.cli;

import it.polimi.ingsw.PSP14.client.model.UIColor;

enum CLIColor implements UIColor {
    RESET("\033[0m"),
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    YELLOW("\033[0;33m"),
    BLUE("\033[0;34m"),
    MAGENTA("\033[0;35m"),
    CYAN("\033[0;36m");

    private String code;

    CLIColor(String code) {
        this.code = code;
    }

    public String toString() {
        return code;
    }
}
