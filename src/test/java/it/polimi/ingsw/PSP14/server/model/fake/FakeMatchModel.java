package it.polimi.ingsw.PSP14.server.model.fake;


import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.board.Board;
import it.polimi.ingsw.PSP14.server.model.board.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FakeMatchModel extends MatchModel {
    public boolean flag;
    public int num;
    public String s;

    public Board board;
    public ArrayList<Player> players;

    public FakeMatchModel() throws IOException {
        super(new MatchController(new ArrayList<>()));
        flag = false;
        num = 0;
        s = "";
        resetBoard();
        resetPlayers();
    }

    @Override
    public Board getBoard() {
        return board;
    }

    public void resetBoard() {
        try {
            this.board = new Board();
        } catch (IOException ignore) {}
    }

    @Override
    public ArrayList<Player> getPlayerMap() {
        return players;
    }

    @Override
    public Player getPlayerByUsername(String username) {
        for(Player p : players) if (p.getUsername().equals(username)) return p;

        return null;
    }

    public void resetPlayers() {
        this.players = new ArrayList<>();
    }
}
