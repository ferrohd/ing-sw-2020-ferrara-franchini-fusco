package it.polimi.ingsw.PSP14.server.model;


import it.polimi.ingsw.PSP14.server.controller.MatchController;

import java.io.IOException;
import java.util.ArrayList;

public class FakeMatchModel extends MatchModel {
    public boolean flag;
    public int num;

    public FakeMatchModel() throws IOException {
        super(new MatchController(new ArrayList<>()));
        flag = false;
        num = 0;
    }
}
